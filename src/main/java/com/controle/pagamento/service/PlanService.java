package com.controle.pagamento.service;

import com.controle.pagamento.dto.SPlan;
import com.stripe.Stripe;
import com.stripe.model.Plan;
import com.stripe.model.PlanCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlanService {

    @Autowired
    private KeyService keyService;

    public List<Plan> getAllPlans() throws Exception {
        // TODO Auto-generated method stub
        Stripe.apiKey = keyService.getKey();
        Map<String, Object> planParams = new HashMap<String, Object>();
        planParams.put("limit", "10");

        PlanCollection plans = Plan.list(planParams);
        List<Plan> p = plans.getData();
        return p;
    }

    public String addPlan(SPlan sp) throws Exception {
        Stripe.apiKey = keyService.getKey();
        String curr = sp.getCurr();
        String intr = sp.getInterval();
        String prod = sp.getProdId();
        String nick = sp.getNickname();
        int amt = sp.getAmt();
        int days = sp.getDays();

        Map<String, Object> params = new HashMap<>();
        params.put("currency", curr);
        params.put("interval", intr);
        params.put("product", prod);
        params.put("nickname", nick);
        params.put("amount", amt);
        params.put("trial_period_days", days);
        Plan plan = Plan.create(params);
        return plan.toString();
    }

    public String deletePlan(String pid) throws Exception {
        // TODO Auto-generated method stub
        Stripe.apiKey = keyService.getKey();
        if(Plan.retrieve(pid)!=null) {
            Plan plan = Plan.retrieve(pid);
            plan.delete();
            return "deleted";
        }else {
            return "no such plan";
        }
    }
}
