package hera.example;

public class Data {

  protected String val1;

  protected int val2;

  public String getVal1() {
    return val1;
  }

  public void setVal1(String val1) {
    this.val1 = val1;
  }

  public int getVal2() {
    return val2;
  }

  public void setVal2(int val2) {
    this.val2 = val2;
  }

  @Override
  public String toString() {
    return String.format("val1: %s, val2: %d", val1, val2);
  }

  @Override
  public boolean equals(final Object obj) {
    if (null == obj || !(obj instanceof Data)) {
      return false;
    }
    final Data other = (Data) obj;
    return val1.equals(other.val1) && val2 == other.val2;
  }

}
