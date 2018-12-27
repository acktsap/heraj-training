package hera.example.lowlevel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    AccountTrainingLowTest.class,
    TransactionTrainingLowTest.class,
    ContractTrainingLowTest.class
})
public class AllTests {

}
