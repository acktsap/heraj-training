package hera.example;

import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import hera.strategy.NettyConnectStrategy;
import hera.strategy.SimpleTimeoutStrategy;

public abstract class AbstractTraining {

  protected AergoClient client;

  public AbstractTraining() {
    this.client = new AergoClientBuilder()
        .addStrategy(new NettyConnectStrategy("localhost:7845"))
        .addStrategy(new SimpleTimeoutStrategy(10000L))
        .build();
  }

  @Override
  protected void finalize() throws Throwable {
    client.close();
  }

}
