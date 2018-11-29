/*
 * @copyright defined in LICENSE.txt
 */

package heraj.example;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    AccountTrainingTest.class,
    TransactionTrainingTest.class,
    ContractTrainingTest.class
})
public class AllTests {

}
