using com.hand.paymentapplicant as  my from '../db/schema';

// @path: 'br'
service CatalogService {
//   entity Books @readonly as projection on my.Books;
  entity Approver  as projection on my.Approve_router ;

  entity paymentapplicant{
    // head;
    item: Association to my.Payment_item;
    list: Association to my.Payment_approve_detail;

  }

  entity Payment_head as projection on my.Payment_head;

  entity Payment_item as projection on my.Payment_item;

  entity Payment_approve_detail as projection on my.Payment_approve_detail;
//   entity paymentapplicant 
  

//   entity Orders @insertonly as projection on my.Orders;
    // entity Orders @readonly as projection on my.Orders;


}

