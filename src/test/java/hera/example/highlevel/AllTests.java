/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.highlevel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    WalletTrainingHighTest.class,
    TransactionTrainingHighTest.class,
    ContractTrainingHighTest.class
})
public class AllTests {

}
