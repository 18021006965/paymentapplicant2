namespace com.hand.paymentapplicant;

using
{
    Country,
    Currency,
    Language,
    User,
    cuid,
    extensible,
    managed,
    temporal
}
from '@sap/cds/common';

entity Payment_head : managed
{
    key ID : UUID;
    key payment_no : String(10);
    applydate : Date;
    applyer : String(30);
    applynotes : String(200);
    item  : Association to many Payment_item on item.head = $self;
    // payment_nolink : Composition of many payment_item on payment_nolink.payment_head = $self;
    approve_result : String(4);
    
}

entity Payment_item
{
    key ID : UUID;
    key payment_no : String(10);
    key payment_item : String(10);
    applymes : String(100);
    applyprice : Currency;
    head : Association to  Payment_head ;
}

entity Approve_router
{
    key approve : String(100);
    approve_name : String(100);
    approve_up : String(100);
}

entity Payment_approve_detail
{
    key ID : UUID;
    key payment_no : String(10);
    key approve_step : String(10);
    approve_note : String(100);
    approve_result : String(4);
    approver :  Association to Approve_router;
    // approver : String(100) Association to one ;
}
