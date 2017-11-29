namespace java com.rbkmoney.geck.filter.test.filter

struct IStatusPaid {
1: string date
2: optional string value;
}

struct IStatusCanceled {
1: required string details
2: optional string value
}
struct IData {
1: required string data_val
2: optional string data_opt_val
}

struct ILvlData {
1: optional ILvl2Data lvl2_data1
2: optional ILvl2Data lvl2_data2
}
struct ILvl2Data {
1: required string il2_val1
2: required string il2_val2
}

union InvoiceStatus {
1: IStatusPaid paid
2: IStatusCanceled canceled
}

union IDataCollection {
1: list<IData> data_list
2: set<IData> data_set
}

struct Invoice {
1: i32 id
2: InvoiceStatus status
3: optional IDataCollection data_coll
5: IData data
6: ILvlData lvl_data
7: optional string i_details
}