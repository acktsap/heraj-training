package hera.example;

public class Data {

  protected int intVal;

  protected String stringVal;

  public int getIntVal() {
    return intVal;
  }

  public void setIntVal(int intVal) {
    this.intVal = intVal;
  }

  public String getStringVal() {
    return stringVal;
  }

  public void setStringVal(String stringVal) {
    this.stringVal = stringVal;
  }

  @Override
  public String toString() {
    return "Data [intVal=" + intVal + ", stringVal=" + stringVal + "]";
  }

  @Override
  public boolean equals(final Object obj) {
    if (null == obj || !(obj instanceof Data)) {
      return false;
    }
    final Data other = (Data) obj;
    return intVal == other.intVal && stringVal.equals(other.stringVal);
  }

}

