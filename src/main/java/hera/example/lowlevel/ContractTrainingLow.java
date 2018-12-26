package hera.example.lowlevel;

import hera.api.model.Account;
import hera.api.model.ContractInterface;
import hera.api.model.ContractTxReceipt;
import hera.example.Data;

public class ContractTrainingLow {

  public ContractInterface deploy(final Account account, final String contractPayload) {
    // TODO : make and use aergoClient to deploy it
    return null;
  }

  public ContractTxReceipt execute(final Account account,
      final ContractInterface contractInterface,
      final String functionName, final Object... args) {
    // TODO : make and use aergoClient to execute it
    return null;
  }

  public Data query(final ContractInterface contractInterface, final String functionName,
      final Object... args) {
    // TODO : make and use aergoClient to query it
    return null;
  }

}
