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

union InvoiceStatus {
1: IStatusPaid paid
2: IStatusCanceled canceled
}

struct Invoice {
1: i32 id
2: InvoiceStatus status
3: IData data
4: optional string i_details
}