package hera.example.highlevel;

import hera.api.model.ContractDefinition;
import hera.api.model.ContractInterface;
import hera.api.model.ContractInvocation;
import hera.api.model.ContractResult;
import hera.api.model.ContractTxHash;
import hera.api.model.ContractTxReceipt;
import hera.api.model.Fee;
import hera.example.Data;
import hera.wallet.Wallet;
import java.io.IOException;

public class ContractTrainingHigh {

  public ContractInterface deploy(final Wallet wallet, final String contractPayload) {
    final ContractDefinition definition = ContractDefinition.newBuilder()
        .encodedContract(contractPayload)
        .build();
    final ContractTxHash deployTxHash = wallet.deploy(definition, Fee.getDefaultFee());
    try {
      Thread.sleep(1500L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    final ContractTxReceipt receipt = wallet.getReceipt(deployTxHash);
    final ContractInterface contractInterface =
        wallet.getContractInterface(receipt.getContractAddress());
    return contractInterface;
  }

  public ContractTxReceipt execute(final Wallet wallet,
      final ContractInterface contractInterface,
      final String functionName, final Object... args) {
    final ContractInvocation execution = contractInterface.newInvocationBuilder()
        .function(functionName)
        .args(args)
        .build();
    final ContractTxHash executeTxHash = wallet.execute(execution, Fee.getDefaultFee());
    try {
      Thread.sleep(1500L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    final ContractTxReceipt receipt = wallet.getReceipt(executeTxHash);
    return receipt;
  }

  public Data query(final Wallet wallet, final ContractInterface contractInterface,
      final String functionName,
      final Object... args) {
    final ContractInvocation query = contractInterface.newInvocationBuilder()
        .function(functionName)
        .args(args)
        .build();
    final ContractResult result = wallet.query(query);
    Data binded = null;
    try {
      binded = result.bind(Data.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return binded;
  }

}
