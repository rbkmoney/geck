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

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused", "Indentation",
        "EmptyLineSeparator",
        "LineLength",
        "MissingSwitchDefault",
        "EmptyBlock",
        "NeedBraces",
        "LeftCurly",
        "LocalVariableName",
        "TypeName",
        "WhitespaceAround",
        "MemberName",
        "ParameterName",
})
@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2020-07-09")
public class Ids3 implements org.apache.thrift.TBase<Ids3, Ids3._Fields>, java.io.Serializable, Cloneable, Comparable<Ids3> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Ids3");

  private static final org.apache.thrift.protocol.TField ANOTHER_MICRO_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("another_micro_id", org.apache.thrift.protocol.TType.BYTE, (short)4);
  private static final org.apache.thrift.protocol.TField ANOTHER_MINI_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("another_mini_id", org.apache.thrift.protocol.TType.I16, (short)3);
  private static final org.apache.thrift.protocol.TField ANOTHER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("another_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField ANOTHER_BIG_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("another_big_id", org.apache.thrift.protocol.TType.I64, (short)1);

  private static final SchemeFactory STANDARD_SCHEME_FACTORY = new Ids3StandardSchemeFactory();
  private static final SchemeFactory TUPLE_SCHEME_FACTORY = new Ids3TupleSchemeFactory();

  public byte another_micro_id; // required
  public short another_mini_id; // required
  public int another_id; // required
  public long another_big_id; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ANOTHER_MICRO_ID((short)4, "another_micro_id"),
    ANOTHER_MINI_ID((short)3, "another_mini_id"),
    ANOTHER_ID((short)2, "another_id"),
    ANOTHER_BIG_ID((short)1, "another_big_id");

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
        case 4: // ANOTHER_MICRO_ID
          return ANOTHER_MICRO_ID;
        case 3: // ANOTHER_MINI_ID
          return ANOTHER_MINI_ID;
        case 2: // ANOTHER_ID
          return ANOTHER_ID;
        case 1: // ANOTHER_BIG_ID
          return ANOTHER_BIG_ID;
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
  private static final int __ANOTHER_MICRO_ID_ISSET_ID = 0;
  private static final int __ANOTHER_MINI_ID_ISSET_ID = 1;
  private static final int __ANOTHER_ID_ISSET_ID = 2;
  private static final int __ANOTHER_BIG_ID_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ANOTHER_MICRO_ID, new org.apache.thrift.meta_data.FieldMetaData("another_micro_id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.ANOTHER_MINI_ID, new org.apache.thrift.meta_data.FieldMetaData("another_mini_id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.ANOTHER_ID, new org.apache.thrift.meta_data.FieldMetaData("another_id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ANOTHER_BIG_ID, new org.apache.thrift.meta_data.FieldMetaData("another_big_id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Ids3.class, metaDataMap);
  }

  public Ids3() {
  }

  public Ids3(
    byte another_micro_id,
    short another_mini_id,
    int another_id,
    long another_big_id)
  {
    this();
    this.another_micro_id = another_micro_id;
    setAnotherMicroIdIsSet(true);
    this.another_mini_id = another_mini_id;
    setAnotherMiniIdIsSet(true);
    this.another_id = another_id;
    setAnotherIdIsSet(true);
    this.another_big_id = another_big_id;
    setAnotherBigIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Ids3(Ids3 other) {
    __isset_bitfield = other.__isset_bitfield;
    this.another_micro_id = other.another_micro_id;
    this.another_mini_id = other.another_mini_id;
    this.another_id = other.another_id;
    this.another_big_id = other.another_big_id;
  }

  public Ids3 deepCopy() {
    return new Ids3(this);
  }

  @Override
  public void clear() {
    setAnotherMicroIdIsSet(false);
    this.another_micro_id = 0;
    setAnotherMiniIdIsSet(false);
    this.another_mini_id = 0;
    setAnotherIdIsSet(false);
    this.another_id = 0;
    setAnotherBigIdIsSet(false);
    this.another_big_id = 0;
  }

  public byte getAnotherMicroId() {
    return this.another_micro_id;
  }

  public Ids3 setAnotherMicroId(byte another_micro_id) {
    this.another_micro_id = another_micro_id;
    setAnotherMicroIdIsSet(true);
    return this;
  }

  public void unsetAnotherMicroId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ANOTHER_MICRO_ID_ISSET_ID);
  }

  /** Returns true if field another_micro_id is set (has been assigned a value) and false otherwise */
  public boolean isSetAnotherMicroId() {
    return EncodingUtils.testBit(__isset_bitfield, __ANOTHER_MICRO_ID_ISSET_ID);
  }

  public void setAnotherMicroIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ANOTHER_MICRO_ID_ISSET_ID, value);
  }

  public short getAnotherMiniId() {
    return this.another_mini_id;
  }

  public Ids3 setAnotherMiniId(short another_mini_id) {
    this.another_mini_id = another_mini_id;
    setAnotherMiniIdIsSet(true);
    return this;
  }

  public void unsetAnotherMiniId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ANOTHER_MINI_ID_ISSET_ID);
  }

  /** Returns true if field another_mini_id is set (has been assigned a value) and false otherwise */
  public boolean isSetAnotherMiniId() {
    return EncodingUtils.testBit(__isset_bitfield, __ANOTHER_MINI_ID_ISSET_ID);
  }

  public void setAnotherMiniIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ANOTHER_MINI_ID_ISSET_ID, value);
  }

  public int getAnotherId() {
    return this.another_id;
  }

  public Ids3 setAnotherId(int another_id) {
    this.another_id = another_id;
    setAnotherIdIsSet(true);
    return this;
  }

  public void unsetAnotherId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ANOTHER_ID_ISSET_ID);
  }

  /** Returns true if field another_id is set (has been assigned a value) and false otherwise */
  public boolean isSetAnotherId() {
    return EncodingUtils.testBit(__isset_bitfield, __ANOTHER_ID_ISSET_ID);
  }

  public void setAnotherIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ANOTHER_ID_ISSET_ID, value);
  }

  public long getAnotherBigId() {
    return this.another_big_id;
  }

  public Ids3 setAnotherBigId(long another_big_id) {
    this.another_big_id = another_big_id;
    setAnotherBigIdIsSet(true);
    return this;
  }

  public void unsetAnotherBigId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ANOTHER_BIG_ID_ISSET_ID);
  }

  /** Returns true if field another_big_id is set (has been assigned a value) and false otherwise */
  public boolean isSetAnotherBigId() {
    return EncodingUtils.testBit(__isset_bitfield, __ANOTHER_BIG_ID_ISSET_ID);
  }

  public void setAnotherBigIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ANOTHER_BIG_ID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ANOTHER_MICRO_ID:
      if (value == null) {
        unsetAnotherMicroId();
      } else {
        setAnotherMicroId((Byte)value);
      }
      break;

    case ANOTHER_MINI_ID:
      if (value == null) {
        unsetAnotherMiniId();
      } else {
        setAnotherMiniId((Short)value);
      }
      break;

    case ANOTHER_ID:
      if (value == null) {
        unsetAnotherId();
      } else {
        setAnotherId((Integer)value);
      }
      break;

    case ANOTHER_BIG_ID:
      if (value == null) {
        unsetAnotherBigId();
      } else {
        setAnotherBigId((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ANOTHER_MICRO_ID:
      return getAnotherMicroId();

    case ANOTHER_MINI_ID:
      return getAnotherMiniId();

    case ANOTHER_ID:
      return getAnotherId();

    case ANOTHER_BIG_ID:
      return getAnotherBigId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ANOTHER_MICRO_ID:
      return isSetAnotherMicroId();
    case ANOTHER_MINI_ID:
      return isSetAnotherMiniId();
    case ANOTHER_ID:
      return isSetAnotherId();
    case ANOTHER_BIG_ID:
      return isSetAnotherBigId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Ids3)
      return this.equals((Ids3)that);
    return false;
  }

  public boolean equals(Ids3 that) {
    if (that == null)
      return false;

    boolean this_present_another_micro_id = true;
    boolean that_present_another_micro_id = true;
    if (this_present_another_micro_id || that_present_another_micro_id) {
      if (!(this_present_another_micro_id && that_present_another_micro_id))
        return false;
      if (this.another_micro_id != that.another_micro_id)
        return false;
    }

    boolean this_present_another_mini_id = true;
    boolean that_present_another_mini_id = true;
    if (this_present_another_mini_id || that_present_another_mini_id) {
      if (!(this_present_another_mini_id && that_present_another_mini_id))
        return false;
      if (this.another_mini_id != that.another_mini_id)
        return false;
    }

    boolean this_present_another_id = true;
    boolean that_present_another_id = true;
    if (this_present_another_id || that_present_another_id) {
      if (!(this_present_another_id && that_present_another_id))
        return false;
      if (this.another_id != that.another_id)
        return false;
    }

    boolean this_present_another_big_id = true;
    boolean that_present_another_big_id = true;
    if (this_present_another_big_id || that_present_another_big_id) {
      if (!(this_present_another_big_id && that_present_another_big_id))
        return false;
      if (this.another_big_id != that.another_big_id)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + (int) (another_micro_id);

    hashCode = hashCode * 8191 + another_mini_id;

    hashCode = hashCode * 8191 + another_id;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(another_big_id);

    return hashCode;
  }

  @Override
  public int compareTo(Ids3 other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetAnotherMicroId()).compareTo(other.isSetAnotherMicroId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAnotherMicroId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.another_micro_id, other.another_micro_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAnotherMiniId()).compareTo(other.isSetAnotherMiniId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAnotherMiniId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.another_mini_id, other.another_mini_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAnotherId()).compareTo(other.isSetAnotherId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAnotherId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.another_id, other.another_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAnotherBigId()).compareTo(other.isSetAnotherBigId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAnotherBigId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.another_big_id, other.another_big_id);
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
    StringBuilder sb = new StringBuilder("Ids3(");
    boolean first = true;

    sb.append("another_micro_id:");
    sb.append(this.another_micro_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("another_mini_id:");
    sb.append(this.another_mini_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("another_id:");
    sb.append(this.another_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("another_big_id:");
    sb.append(this.another_big_id);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'another_micro_id' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'another_mini_id' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'another_id' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'another_big_id' because it's a primitive and you chose the non-beans generator.
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

  private static class Ids3StandardSchemeFactory implements SchemeFactory {
    public Ids3StandardScheme getScheme() {
      return new Ids3StandardScheme();
    }
  }

  private static class Ids3StandardScheme extends StandardScheme<Ids3> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Ids3 struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 4: // ANOTHER_MICRO_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.another_micro_id = iprot.readByte();
              struct.setAnotherMicroIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ANOTHER_MINI_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.another_mini_id = iprot.readI16();
              struct.setAnotherMiniIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ANOTHER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.another_id = iprot.readI32();
              struct.setAnotherIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 1: // ANOTHER_BIG_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.another_big_id = iprot.readI64();
              struct.setAnotherBigIdIsSet(true);
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
      if (!struct.isSetAnotherMicroId()) {
        throw new TProtocolException("Required field 'another_micro_id' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetAnotherMiniId()) {
        throw new TProtocolException("Required field 'another_mini_id' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetAnotherId()) {
        throw new TProtocolException("Required field 'another_id' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetAnotherBigId()) {
        throw new TProtocolException("Required field 'another_big_id' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Ids3 struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ANOTHER_BIG_ID_FIELD_DESC);
      oprot.writeI64(struct.another_big_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ANOTHER_ID_FIELD_DESC);
      oprot.writeI32(struct.another_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ANOTHER_MINI_ID_FIELD_DESC);
      oprot.writeI16(struct.another_mini_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ANOTHER_MICRO_ID_FIELD_DESC);
      oprot.writeByte(struct.another_micro_id);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class Ids3TupleSchemeFactory implements SchemeFactory {
    public Ids3TupleScheme getScheme() {
      return new Ids3TupleScheme();
    }
  }

  private static class Ids3TupleScheme extends TupleScheme<Ids3> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Ids3 struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeByte(struct.another_micro_id);
      oprot.writeI16(struct.another_mini_id);
      oprot.writeI32(struct.another_id);
      oprot.writeI64(struct.another_big_id);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Ids3 struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.another_micro_id = iprot.readByte();
      struct.setAnotherMicroIdIsSet(true);
      struct.another_mini_id = iprot.readI16();
      struct.setAnotherMiniIdIsSet(true);
      struct.another_id = iprot.readI32();
      struct.setAnotherIdIsSet(true);
      struct.another_big_id = iprot.readI64();
      struct.setAnotherBigIdIsSet(true);
    }
  }

  private static <S extends IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

