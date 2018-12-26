package hera.example.lowlevel;

import hera.api.model.Account;
import hera.api.model.ContractDefinition;
import hera.api.model.ContractInterface;
import hera.api.model.ContractInvocation;
import hera.api.model.ContractResult;
import hera.api.model.ContractTxHash;
import hera.api.model.ContractTxReceipt;
import hera.api.model.Fee;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import hera.example.Data;
import java.io.IOException;

public class ContractTrainingLow {

  public ContractInterface deploy(final Account account, final String contractPayload) {
    final AergoClient aergoClient = new AergoClientBuilder()
        .withNonBlockingConnect()
        .withEndpoint("localhost:7845")
        .build();
    final ContractDefinition definition = ContractDefinition.newBuilder()
        .encodedContract(contractPayload)
        .build();
    final ContractTxHash deployTxHash =
        aergoClient.getContractOperation().deploy(account, definition,
            account.incrementAndGetNonce(), Fee.getDefaultFee());
    try {
      Thread.sleep(1500L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    final ContractTxReceipt receipt = aergoClient.getContractOperation().getReceipt(deployTxHash);
    final ContractInterface contractInterface =
        aergoClient.getContractOperation().getContractInterface(receipt.getContractAddress());
    aergoClient.close();
    return contractInterface;
  }

  public ContractTxReceipt execute(final Account account,
      final ContractInterface contractInterface,
      final String functionName, final Object... args) {
    final AergoClient aergoClient = new AergoClientBuilder()
        .withNonBlockingConnect()
        .withEndpoint("localhost:7845")
        .build();
    final ContractInvocation execution = contractInterface.newInvocationBuilder()
        .function(functionName)
        .args(args)
        .build();
    final ContractTxHash executeTxHash = aergoClient.getContractOperation().execute(account,
        execution, account.incrementAndGetNonce(), Fee.getDefaultFee());
    try {
      Thread.sleep(1500L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    final ContractTxReceipt receipt = aergoClient.getContractOperation().getReceipt(executeTxHash);
    aergoClient.close();
    return receipt;
  }

  public Data query(final ContractInterface contractInterface, final String functionName,
      final Object... args) {
    final AergoClient aergoClient = new AergoClientBuilder()
        .withNonBlockingConnect()
        .withEndpoint("localhost:7845")
        .build();
    final ContractInvocation query = contractInterface.newInvocationBuilder()
        .function(functionName)
        .args(args)
        .build();
    final ContractResult result = aergoClient.getContractOperation().query(query);
    Data binded = null;
    try {
      binded = result.bind(Data.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    aergoClient.close();
    return binded;
  }

}
