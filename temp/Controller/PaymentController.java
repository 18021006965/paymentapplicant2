package customer.paymentapplicant.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONObject;
import org.json.JSONArray;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import customer.paymentapplicant.Util.Tool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.sap.cds.feature.xsuaa.XsuaaUserInfo;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.Result;
import com.sap.cds.Row;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.catalogservice.PaymentHead_;
import cds.gen.catalogservice.PaymentItem_;
import cds.gen.catalogservice.PaymentApproveDetail_;
import cds.gen.catalogservice.PaymentHead;
import cds.gen.catalogservice.PaymentItem;
import cds.gen.catalogservice.PaymentApproveDetail;
//import cds.gen.catalogservice.*;

import java.util.Map;
import java.util.Set;

@Tag(name = "控制器")
@RestController
@RequestMapping(value = "/con")

public class PaymentController {

    @Autowired
    PersistenceService db;

    @Autowired
    XsuaaUserInfo xsuaaUserInfo;

    @GetMapping("/xsuaaUserInfo")
    @ResponseBody
    public XsuaaUserInfo xsuaaUserInfo() {
        return xsuaaUserInfo;
    }

    // @ApiOperation("测试接口1")
    // @PostMapping("/show1")
    // public String show1(@ApiParam(value = "姓名", required = true, example = "村雨遥")
    // @RequestBody String name) {
    // return "hello," + name + ",welcome to springboot swagger3！";
    // }

    @Operation(summary = "请款单清单")
    @GetMapping("/contextInfo")
    @ResponseBody
    public String SetContext() {

        CqnSelect sel_head = Select.from(PaymentHead_.class);
        // .where(head ->
        // head.approve_result().eq("")
        // .and(ApprovalAuthorityQAC.E_PREQ_NO().eq(E_PREQ_NO))
        // .and(ApprovalAuthorityQAC.E_PREQ_ANLKL().contains(E_PREQ_ANLKL))
        // .and(ApprovalAuthorityQAC.P().contains("X"))
        // );
        JSONArray jsonArray = new JSONArray();

        
        Result rs = db.run(sel_head);
        rs.forEach(rl -> {
            JSONObject JSONObject_Payment = new JSONObject();
            JSONObject_Payment.put("createdAt", rl.get("createdAt").toString());
            JSONObject_Payment.put("applyer", rl.get("applyer").toString());
            String payment_no_str = rl.get("payment_no").toString();
            JSONObject_Payment.put("payment_no", rl.get("payment_no").toString());
            JSONObject_Payment.put("approve_result", rl.get("approve_result").toString());
            //
            CqnSelect sel_item = Select.from(PaymentItem_.class)
                    .where(item -> item.payment_no().eq(payment_no_str));
            //
            Result rs_item = db.run(sel_item);

            JSONArray jsonArray_item = new JSONArray();
            rs_item.forEach(rl_item -> {
                JSONObject JSONObject_Item = new JSONObject();
                JSONObject_Item.put("payment_item", rl_item.get("payment_item").toString());
                JSONObject_Item.put("applymes", rl_item.get("applymes").toString());
                JSONObject_Item.put("applyprice", rl_item.get("applyprice"));
                jsonArray_item.put(JSONObject_Item);
            });
            // String item_json = jsonArray_item.toString();
            // JSONObject_Payment.put("item",jsonArray_item.toString());
            JSONObject_Payment.put("item", jsonArray_item);
            jsonArray.put(JSONObject_Payment);

            CqnSelect sel_approve = Select.from(PaymentApproveDetail_.class);
            // .where(approve -> approve.payment_no().eq(payment_no_str)
            // );
            Result rs_approve = db.run(sel_approve);
            JSONArray jsonArray_approve = new JSONArray();
            rs_approve.forEach(rl_approve -> {
                JSONObject JSONObject_Approve = new JSONObject();
                JSONObject_Approve.put("approve_step", rl_approve.get("approve_step").toString());
                JSONObject_Approve.put("approve_note", rl_approve.get("approve_note").toString());
                JSONObject_Approve.put("approve_result", rl_approve.get("approve_result").toString());
                JSONObject_Approve.put("approver_approve", rl_approve.get("approver_approve").toString());
                jsonArray_approve.put(JSONObject_Approve);
            });
            // String item_json = jsonArray_item.toString();
            // JSONObject_Payment.put("item",jsonArray_item.toString());
            JSONObject_Payment.put("approve", jsonArray_approve);
        });

        return jsonArray.toString();

        // rs.forEach((rs)->{System.out.println(rs.toJson());});

        // for (Row row : rs) {
        //
        //// PaymentHead tmp = row.as(PaymentHead.class);
        // // rtn.add(tmp);
        //
        // break;
        // }

        // return .toString();

        // // JSONArray result = new JSONArray();
        //// while (resultSet.next()) {
        //// JSONObject row = new JSONObject();
        //// colNames.forEach(cn -> {
        //// try {
        //// row.put(cn, resultSet.getObject(cn));
        //// } catch (JSONException | SQLException e) {
        //// e.printStackTrace();
        //// }
        //// });
        //// result.add(row);
        //// }
        // String string = rs.toString();
        // System.out.println(rs.toString());
        //// lombok.val rsJson = rs.toJson();

    }

    // @ApiOperation("测试接口1")
    // @PostMapping("/show1")
    // public String show1(@ApiParam(value = "姓名", required = true, example = "村雨遥")
    // @RequestBody String name) {
    // return "hello," + name + ",welcome to springboot swagger3！";
    // }
    //
    // @ApiOperation("测试接口2")
    // @PostMapping("/show2")
    // public String show2(@ApiParam(value = "用户对象", required = true) @RequestBody
    // String user) {
    // return "hello," + user + ",welcome to springboot swagger3！";
    // }

}
