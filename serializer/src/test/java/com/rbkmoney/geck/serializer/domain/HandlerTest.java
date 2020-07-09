/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.rbkmoney.geck.serializer.domain;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2020-07-09")
public class HandlerTest implements org.apache.thrift.TBase<HandlerTest, HandlerTest._Fields>, java.io.Serializable, Cloneable, Comparable<HandlerTest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HandlerTest");

  private static final org.apache.thrift.protocol.TField ONE_FIELD_DESC = new org.apache.thrift.protocol.TField("one", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TWO_FIELD_DESC = new org.apache.thrift.protocol.TField("two", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField THREE_FIELD_DESC = new org.apache.thrift.protocol.TField("three", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final SchemeFactory STANDARD_SCHEME_FACTORY = new HandlerTestStandardSchemeFactory();
  private static final SchemeFactory TUPLE_SCHEME_FACTORY = new HandlerTestTupleSchemeFactory();

  public String one; // required
  public int two; // required
  public List<String> three; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ONE((short)1, "one"),
    TWO((short)2, "two"),
    THREE((short)3, "three");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ONE
          return ONE;
        case 2: // TWO
          return TWO;
        case 3: // THREE
          return THREE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TWO_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ONE, new org.apache.thrift.meta_data.FieldMetaData("one", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TWO, new org.apache.thrift.meta_data.FieldMetaData("two", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.THREE, new org.apache.thrift.meta_data.FieldMetaData("three", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HandlerTest.class, metaDataMap);
  }

  public HandlerTest() {
  }

  public HandlerTest(
    String one,
    int two,
    List<String> three)
  {
    this();
    this.one = one;
    this.two = two;
    setTwoIsSet(true);
    this.three = three;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HandlerTest(HandlerTest other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetOne()) {
      this.one = other.one;
    }
    this.two = other.two;
    if (other.isSetThree()) {
      List<String> __this__three = new ArrayList<String>(other.three);
      this.three = __this__three;
    }
  }

  public HandlerTest deepCopy() {
    return new HandlerTest(this);
  }

  @Override
  public void clear() {
    this.one = null;
    setTwoIsSet(false);
    this.two = 0;
    this.three = null;
  }

  public String getOne() {
    return this.one;
  }

  public HandlerTest setOne(String one) {
    this.one = one;
    return this;
  }

  public void unsetOne() {
    this.one = null;
  }

  /** Returns true if field one is set (has been assigned a value) and false otherwise */
  public boolean isSetOne() {
    return this.one != null;
  }

  public void setOneIsSet(boolean value) {
    if (!value) {
      this.one = null;
    }
  }

  public int getTwo() {
    return this.two;
  }

  public HandlerTest setTwo(int two) {
    this.two = two;
    setTwoIsSet(true);
    return this;
  }

  public void unsetTwo() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TWO_ISSET_ID);
  }

  /** Returns true if field two is set (has been assigned a value) and false otherwise */
  public boolean isSetTwo() {
    return EncodingUtils.testBit(__isset_bitfield, __TWO_ISSET_ID);
  }

  public void setTwoIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TWO_ISSET_ID, value);
  }

  public int getThreeSize() {
    return (this.three == null) ? 0 : this.three.size();
  }

  public java.util.Iterator<String> getThreeIterator() {
    return (this.three == null) ? null : this.three.iterator();
  }

  public void addToThree(String elem) {
    if (this.three == null) {
      this.three = new ArrayList<String>();
    }
    this.three.add(elem);
  }

  public List<String> getThree() {
    return this.three;
  }

  public HandlerTest setThree(List<String> three) {
    this.three = three;
    return this;
  }

  public void unsetThree() {
    this.three = null;
  }

  /** Returns true if field three is set (has been assigned a value) and false otherwise */
  public boolean isSetThree() {
    return this.three != null;
  }

  public void setThreeIsSet(boolean value) {
    if (!value) {
      this.three = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ONE:
      if (value == null) {
        unsetOne();
      } else {
        setOne((String)value);
      }
      break;

    case TWO:
      if (value == null) {
        unsetTwo();
      } else {
        setTwo((Integer)value);
      }
      break;

    case THREE:
      if (value == null) {
        unsetThree();
      } else {
        setThree((List<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ONE:
      return getOne();

    case TWO:
      return getTwo();

    case THREE:
      return getThree();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ONE:
      return isSetOne();
    case TWO:
      return isSetTwo();
    case THREE:
      return isSetThree();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HandlerTest)
      return this.equals((HandlerTest)that);
    return false;
  }

  public boolean equals(HandlerTest that) {
    if (that == null)
      return false;

    boolean this_present_one = true && this.isSetOne();
    boolean that_present_one = true && that.isSetOne();
    if (this_present_one || that_present_one) {
      if (!(this_present_one && that_present_one))
        return false;
      if (!this.one.equals(that.one))
        return false;
    }

    boolean this_present_two = true;
    boolean that_present_two = true;
    if (this_present_two || that_present_two) {
      if (!(this_present_two && that_present_two))
        return false;
      if (this.two != that.two)
        return false;
    }

    boolean this_present_three = true && this.isSetThree();
    boolean that_present_three = true && that.isSetThree();
    if (this_present_three || that_present_three) {
      if (!(this_present_three && that_present_three))
        return false;
      if (!this.three.equals(that.three))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetOne()) ? 131071 : 524287);
    if (isSetOne())
      hashCode = hashCode * 8191 + one.hashCode();

    hashCode = hashCode * 8191 + two;

    hashCode = hashCode * 8191 + ((isSetThree()) ? 131071 : 524287);
    if (isSetThree())
      hashCode = hashCode * 8191 + three.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(HandlerTest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOne()).compareTo(other.isSetOne());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOne()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.one, other.one);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTwo()).compareTo(other.isSetTwo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTwo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.two, other.two);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetThree()).compareTo(other.isSetThree());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetThree()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.three, other.three);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public _Fields[] getFields() {
    return _Fields.values();
  }

  public Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> getFieldMetaData() {
    return metaDataMap;
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("HandlerTest(");
    boolean first = true;

    sb.append("one:");
    if (this.one == null) {
      sb.append("null");
    } else {
      sb.append(this.one);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("two:");
    sb.append(this.two);
    first = false;
    if (!first) sb.append(", ");
    sb.append("three:");
    if (this.three == null) {
      sb.append("null");
    } else {
      sb.append(this.three);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (one == null) {
      throw new TProtocolException("Required field 'one' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'two' because it's a primitive and you chose the non-beans generator.
    if (three == null) {
      throw new TProtocolException("Required field 'three' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class HandlerTestStandardSchemeFactory implements SchemeFactory {
    public HandlerTestStandardScheme getScheme() {
      return new HandlerTestStandardScheme();
    }
  }

  private static class HandlerTestStandardScheme extends StandardScheme<HandlerTest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HandlerTest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // ONE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.one = iprot.readString();
              struct.setOneIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TWO
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.two = iprot.readI32();
              struct.setTwoIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // THREE
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list134 = iprot.readListBegin();
                struct.three = new ArrayList<String>(_list134.size);
                String _elem135;
                for (int _i136 = 0; _i136 < _list134.size; ++_i136)
                {
                  _elem135 = iprot.readString();
                  struct.three.add(_elem135);
                }
                iprot.readListEnd();
              }
              struct.setThreeIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetTwo()) {
        throw new TProtocolException("Required field 'two' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, HandlerTest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.one != null) {
        oprot.writeFieldBegin(ONE_FIELD_DESC);
        oprot.writeString(struct.one);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TWO_FIELD_DESC);
      oprot.writeI32(struct.two);
      oprot.writeFieldEnd();
      if (struct.three != null) {
        oprot.writeFieldBegin(THREE_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.three.size()));
          for (String _iter137 : struct.three)
          {
            oprot.writeString(_iter137);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HandlerTestTupleSchemeFactory implements SchemeFactory {
    public HandlerTestTupleScheme getScheme() {
      return new HandlerTestTupleScheme();
    }
  }

  private static class HandlerTestTupleScheme extends TupleScheme<HandlerTest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HandlerTest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.one);
      oprot.writeI32(struct.two);
      {
        oprot.writeI32(struct.three.size());
        for (String _iter138 : struct.three)
        {
          oprot.writeString(_iter138);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HandlerTest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.one = iprot.readString();
      struct.setOneIsSet(true);
      struct.two = iprot.readI32();
      struct.setTwoIsSet(true);
      {
        org.apache.thrift.protocol.TList _list139 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
        struct.three = new ArrayList<String>(_list139.size);
        String _elem140;
        for (int _i141 = 0; _i141 < _list139.size; ++_i141)
        {
          _elem140 = iprot.readString();
          struct.three.add(_elem140);
        }
      }
      struct.setThreeIsSet(true);
    }
  }

  private static <S extends IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

