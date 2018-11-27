/*
 * @copyright defined in LICENSE.txt
 */

package heraj.example;

public abstract class AbstractTestCase {

  protected void waitingForNextBlockToGenerate() {
    try {
      Thread.sleep(1500L);
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }

}
