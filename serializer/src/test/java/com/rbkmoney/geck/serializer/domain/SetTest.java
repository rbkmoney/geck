package com.rbkmoney.geck.serializer.domain;

import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2020-07-09")
public class SetTest implements org.apache.thrift.TBase<SetTest, SetTest._Fields>, java.io.Serializable, Cloneable, Comparable<SetTest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SetTest");

  private static final org.apache.thrift.protocol.TField IDS_SET_FIELD_DESC = new org.apache.thrift.protocol.TField("idsSet", org.apache.thrift.protocol.TType.SET, (short)1);
  private static final org.apache.thrift.protocol.TField STATUS_SET_FIELD_DESC = new org.apache.thrift.protocol.TField("statusSet", org.apache.thrift.protocol.TType.SET, (short)2);

  private static final SchemeFactory STANDARD_SCHEME_FACTORY = new SetTestStandardSchemeFactory();
  private static final SchemeFactory TUPLE_SCHEME_FACTORY = new SetTestTupleSchemeFactory();

  public Set<Ids> idsSet; // required
  public Set<Status> statusSet; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IDS_SET((short)1, "idsSet"),
    STATUS_SET((short)2, "statusSet");

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
        case 1: // IDS_SET
          return IDS_SET;
        case 2: // STATUS_SET
          return STATUS_SET;
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
  private static final _Fields optionals[] = {_Fields.STATUS_SET};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IDS_SET, new org.apache.thrift.meta_data.FieldMetaData("idsSet", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Ids.class))));
    tmpMap.put(_Fields.STATUS_SET, new org.apache.thrift.meta_data.FieldMetaData("statusSet", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Status.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SetTest.class, metaDataMap);
  }

  public SetTest() {
  }

  public SetTest(
    Set<Ids> idsSet)
  {
    this();
    this.idsSet = idsSet;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SetTest(SetTest other) {
    if (other.isSetIdsSet()) {
      Set<Ids> __this__idsSet = new HashSet<Ids>(other.idsSet.size());
      for (Ids other_element : other.idsSet) {
        __this__idsSet.add(other_element);
      }
      this.idsSet = __this__idsSet;
    }
    if (other.isSetStatusSet()) {
      Set<Status> __this__statusSet = new HashSet<Status>(other.statusSet.size());
      for (Status other_element : other.statusSet) {
        __this__statusSet.add(other_element);
      }
      this.statusSet = __this__statusSet;
    }
  }

  public SetTest deepCopy() {
    return new SetTest(this);
  }

  @Override
  public void clear() {
    this.idsSet = null;
    this.statusSet = null;
  }

  public int getIdsSetSize() {
    return (this.idsSet == null) ? 0 : this.idsSet.size();
  }

  public java.util.Iterator<Ids> getIdsSetIterator() {
    return (this.idsSet == null) ? null : this.idsSet.iterator();
  }

  public void addToIdsSet(Ids elem) {
    if (this.idsSet == null) {
      this.idsSet = new HashSet<Ids>();
    }
    this.idsSet.add(elem);
  }

  public Set<Ids> getIdsSet() {
    return this.idsSet;
  }

  public SetTest setIdsSet(Set<Ids> idsSet) {
    this.idsSet = idsSet;
    return this;
  }

  public void unsetIdsSet() {
    this.idsSet = null;
  }

  /** Returns true if field idsSet is set (has been assigned a value) and false otherwise */
  public boolean isSetIdsSet() {
    return this.idsSet != null;
  }

  public void setIdsSetIsSet(boolean value) {
    if (!value) {
      this.idsSet = null;
    }
  }

  public int getStatusSetSize() {
    return (this.statusSet == null) ? 0 : this.statusSet.size();
  }

  public java.util.Iterator<Status> getStatusSetIterator() {
    return (this.statusSet == null) ? null : this.statusSet.iterator();
  }

  public void addToStatusSet(Status elem) {
    if (this.statusSet == null) {
      this.statusSet = new HashSet<Status>();
    }
    this.statusSet.add(elem);
  }

  public Set<Status> getStatusSet() {
    return this.statusSet;
  }

  public SetTest setStatusSet(Set<Status> statusSet) {
    this.statusSet = statusSet;
    return this;
  }

  public void unsetStatusSet() {
    this.statusSet = null;
  }

  /** Returns true if field statusSet is set (has been assigned a value) and false otherwise */
  public boolean isSetStatusSet() {
    return this.statusSet != null;
  }

  public void setStatusSetIsSet(boolean value) {
    if (!value) {
      this.statusSet = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case IDS_SET:
      if (value == null) {
        unsetIdsSet();
      } else {
        setIdsSet((Set<Ids>)value);
      }
      break;

    case STATUS_SET:
      if (value == null) {
        unsetStatusSet();
      } else {
        setStatusSet((Set<Status>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case IDS_SET:
      return getIdsSet();

    case STATUS_SET:
      return getStatusSet();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case IDS_SET:
      return isSetIdsSet();
    case STATUS_SET:
      return isSetStatusSet();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SetTest)
      return this.equals((SetTest)that);
    return false;
  }

  public boolean equals(SetTest that) {
    if (that == null)
      return false;

    boolean this_present_idsSet = true && this.isSetIdsSet();
    boolean that_present_idsSet = true && that.isSetIdsSet();
    if (this_present_idsSet || that_present_idsSet) {
      if (!(this_present_idsSet && that_present_idsSet))
        return false;
      if (!this.idsSet.equals(that.idsSet))
        return false;
    }

    boolean this_present_statusSet = true && this.isSetStatusSet();
    boolean that_present_statusSet = true && that.isSetStatusSet();
    if (this_present_statusSet || that_present_statusSet) {
      if (!(this_present_statusSet && that_present_statusSet))
        return false;
      if (!this.statusSet.equals(that.statusSet))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetIdsSet()) ? 131071 : 524287);
    if (isSetIdsSet())
      hashCode = hashCode * 8191 + idsSet.hashCode();

    hashCode = hashCode * 8191 + ((isSetStatusSet()) ? 131071 : 524287);
    if (isSetStatusSet())
      hashCode = hashCode * 8191 + statusSet.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(SetTest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetIdsSet()).compareTo(other.isSetIdsSet());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIdsSet()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.idsSet, other.idsSet);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStatusSet()).compareTo(other.isSetStatusSet());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatusSet()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.statusSet, other.statusSet);
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
    StringBuilder sb = new StringBuilder("SetTest(");
    boolean first = true;

    sb.append("idsSet:");
    if (this.idsSet == null) {
      sb.append("null");
    } else {
      sb.append(this.idsSet);
    }
    first = false;
    if (isSetStatusSet()) {
      if (!first) sb.append(", ");
      sb.append("statusSet:");
      if (this.statusSet == null) {
        sb.append("null");
      } else {
        sb.append(this.statusSet);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (idsSet == null) {
      throw new TProtocolException("Required field 'idsSet' was not present! Struct: " + toString());
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class SetTestStandardSchemeFactory implements SchemeFactory {
    public SetTestStandardScheme getScheme() {
      return new SetTestStandardScheme();
    }
  }

  private static class SetTestStandardScheme extends StandardScheme<SetTest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SetTest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IDS_SET
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set88 = iprot.readSetBegin();
                struct.idsSet = new HashSet<Ids>(2*_set88.size);
                Ids _elem89;
                for (int _i90 = 0; _i90 < _set88.size; ++_i90)
                {
                  _elem89 = new Ids();
                  _elem89.read(iprot);
                  struct.idsSet.add(_elem89);
                }
                iprot.readSetEnd();
              }
              struct.setIdsSetIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // STATUS_SET
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set91 = iprot.readSetBegin();
                struct.statusSet = new HashSet<Status>(2*_set91.size);
                Status _elem92;
                for (int _i93 = 0; _i93 < _set91.size; ++_i93)
                {
                  _elem92 = new Status();
                  _elem92.read(iprot);
                  struct.statusSet.add(_elem92);
                }
                iprot.readSetEnd();
              }
              struct.setStatusSetIsSet(true);
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SetTest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.idsSet != null) {
        oprot.writeFieldBegin(IDS_SET_FIELD_DESC);
        {
          oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRUCT, struct.idsSet.size()));
          for (Ids _iter94 : struct.idsSet)
          {
            _iter94.write(oprot);
          }
          oprot.writeSetEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.statusSet != null) {
        if (struct.isSetStatusSet()) {
          oprot.writeFieldBegin(STATUS_SET_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRUCT, struct.statusSet.size()));
            for (Status _iter95 : struct.statusSet)
            {
              _iter95.write(oprot);
            }
            oprot.writeSetEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SetTestTupleSchemeFactory implements SchemeFactory {
    public SetTestTupleScheme getScheme() {
      return new SetTestTupleScheme();
    }
  }

  private static class SetTestTupleScheme extends TupleScheme<SetTest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SetTest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.idsSet.size());
        for (Ids _iter96 : struct.idsSet)
        {
          _iter96.write(oprot);
        }
      }
      BitSet optionals = new BitSet();
      if (struct.isSetStatusSet()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetStatusSet()) {
        {
          oprot.writeI32(struct.statusSet.size());
          for (Status _iter97 : struct.statusSet)
          {
            _iter97.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SetTest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TSet _set98 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.idsSet = new HashSet<Ids>(2*_set98.size);
        Ids _elem99;
        for (int _i100 = 0; _i100 < _set98.size; ++_i100)
        {
          _elem99 = new Ids();
          _elem99.read(iprot);
          struct.idsSet.add(_elem99);
        }
      }
      struct.setIdsSetIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TSet _set101 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.statusSet = new HashSet<Status>(2*_set101.size);
          Status _elem102;
          for (int _i103 = 0; _i103 < _set101.size; ++_i103)
          {
            _elem102 = new Status();
            _elem102.read(iprot);
            struct.statusSet.add(_elem102);
          }
        }
        struct.setStatusSetIsSet(true);
      }
    }
  }

  private static <S extends IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

