package com.dengjinwen.basetool.library.function.expression.core;


import com.dengjinwen.basetool.library.R;

import java.util.HashMap;

/**
 * Created by jian on 2016/6/23.
 * mabeijianxi@gmail.com
 */
public class ExpressionCache {
    //    TODO 每页数据资源，可自己定义，自己更改
    public static final String[] page_1 = new String[]{
            "[jx]aa_keai[/jx]", "[jx]ab_haha[/jx]", "[jx]ac_xixi[/jx]",
            "[jx]ad_xiaoku[/jx]", "[jx]ae_sikao[/jx]", "[jx]af_numa[/jx]",
            "[jx]ag_xu[/jx]", "[jx]ah_guzhang[/jx]", "[jx]ai_zuohengheng[/jx]",
            "[jx]aj_youhengheng[/jx]", "[jx]ak_qinqin[/jx]", "[jx]al_yiwen[/jx]",
            "[jx]am_baibai[/jx]", "[jx]an_hehe[/jx]", "[jx]ao_yinxian[/jx]",
            "[jx]ap_haixiu[/jx]", "[jx]aq_jiyan[/jx]", "[jx]ar_dalian[/jx]",
            "[jx]as_nu[/jx]", "[jx]at_lei[/jx]", ""

    };
    public static final String[] page_2 = new String[]{
            "[jx]ba_bishi[/jx]", "[jx]bb_chanzui[/jx]", "[jx]bc_chijing[/jx]",
            "[jx]bd_dahaqi[/jx]", "[jx]be_beishang[/jx]", "[jx]bf_bizui[/jx]",
            "[jx]bg_ding[/jx]", "[jx]bh_ganmao[/jx]", "[jx]bi_han[/jx]",
            "[jx]bj_aini[/jx]", "[jx]bk_heixian[/jx]", "[jx]bl_heng[/jx]",
            "[jx]bm_huaxin[/jx]", "[jx]bn_kelian[/jx]", "[jx]bo_ku[/jx]",
            "[jx]bp_kun[/jx]", "[jx]bq_landelini[/jx]", "[jx]br_qian[/jx]",
            "[jx]bs_shayan[/jx]", "[jx]bt_shengbing[/jx]", ""
    };
    public static final String[] page_3 = new String[]{
            "[jx]ca_shiwang[/jx]", "[jx]cb_shuijiao[/jx]", "[jx]cc_zhuakuang[/jx]",
            "[jx]cd_taikaixin[/jx]", "[jx]ce_touxiao[/jx]", "[jx]cf_tu[/jx]",
            "[jx]cg_wabishi[/jx]", "[jx]ch_weiqu[/jx]", "[jx]ci_yun[/jx]",
            "[jx]cj_shuai[/jx]", "[jx]ck_doge[/jx]", "[jx]cl_miao[/jx]",
            "[jx]cm_xiongmao[/jx]", "[jx]cn_tuzi[/jx]", "[jx]co_zhutou[/jx]",
            "[jx]cp_shenshou[/jx]", "[jx]cq_nanhaier[/jx]", "[jx]cr_nvhaier[/jx]",
            "[jx]cs_feizao[/jx]", "[jx]ct_aoteman[/jx]", ""
    };
    public static final String[] page_4 = new String[]{
            "[jx]da_geili[/jx]", "[jx]db_jiong[/jx]", "[jx]dc_meng[/jx]",
            "[jx]dd_shenma[/jx]", "[jx]de_v5[/jx]", "[jx]df_xi[/jx]",
            "[jx]dg_zhi[/jx]", "[jx]dh_buyao[/jx]", "[jx]di_good[/jx]",
            "[jx]dj_haha[/jx]", "[jx]dk_lai[/jx]", "[jx]dl_ok[/jx]",
            "[jx]dm_ruo[/jx]", "[jx]dn_woshou[/jx]", "[jx]do_ye[/jx]",
            "[jx]dp_zan[/jx]", "[jx]dq_zuoyi[/jx]", "[jx]dr_shangxin[/jx]",
            "[jx]ds_xin[/jx]", "[jx]dt_dangao[/jx]", ""
    };
    public static final String[] page_5 = new String[]{
            "[jx]ea_feiji[/jx]", "[jx]eb_ganbei[/jx]", "[jx]ec_huatong[/jx]",
            "[jx]ed_lazhu[/jx]", "[jx]ee_liwu[/jx]", "[jx]ef_lvsidai[/jx]",
            "[jx]eg_weibo[/jx]", "[jx]eh_weiguan[/jx]", "[jx]ei_yinyue[/jx]",
            "[jx]ej_zhaoxiangji[/jx]", "[jx]ek_zhong[/jx]", "[jx]el_weifeng[/jx]",
            "[jx]em_xianhua[/jx]", "[jx]en_taiyang[/jx]", "[jx]eo_yueliang[/jx]",
            "[jx]ep_fuyun[/jx]", "[jx]eq_xiayu[/jx]", "[jx]er_shachenbao[/jx]",
            "", "", ""
    };

    /**
     * 所有的表情资源id缓存，增加效率
     */
    private static HashMap<String, Integer> allExpressionTable = new HashMap<String, Integer>();

    static {

        allExpressionTable.put(page_1[0], R.mipmap.aa_keai
        );
        allExpressionTable.put(page_1[1], R.mipmap.ab_haha
        );
        allExpressionTable.put(page_1[2], R.mipmap.ac_xixi
        );
        allExpressionTable.put(page_1[3], R.mipmap.ad_xiaoku
        );
        allExpressionTable.put(page_1[4], R.mipmap.ae_sikao
        );
        allExpressionTable.put(page_1[5], R.mipmap.af_numa
        );
        allExpressionTable.put(page_1[6], R.mipmap.ag_xu
        );
        allExpressionTable.put(page_1[7], R.mipmap.ah_guzhang
        );
        allExpressionTable.put(page_1[8], R.mipmap.ai_zuohengheng
        );
        allExpressionTable.put(page_1[9], R.mipmap.aj_youhengheng
        );
        allExpressionTable.put(page_1[10], R.mipmap.ak_qinqin
        );
        allExpressionTable.put(page_1[11], R.mipmap.al_yiwen
        );
        allExpressionTable.put(page_1[12], R.mipmap.am_baibai
        );
        allExpressionTable.put(page_1[13], R.mipmap.an_hehe
        );
        allExpressionTable.put(page_1[14], R.mipmap.ao_yinxian
        );
        allExpressionTable.put(page_1[15], R.mipmap.ap_haixiu
        );
        allExpressionTable.put(page_1[16], R.mipmap.aq_jiyan
        );
        allExpressionTable.put(page_1[17], R.mipmap.ar_dalian
        );
        allExpressionTable.put(page_1[18], R.mipmap.as_nu
        );
        allExpressionTable.put(page_1[19], R.mipmap.at_lei
        );


        allExpressionTable.put(page_2[0], R.mipmap.ba_bishi
        );
        allExpressionTable.put(page_2[1], R.mipmap.bb_chanzui
        );
        allExpressionTable.put(page_2[2], R.mipmap.bc_chijing
        );
        allExpressionTable.put(page_2[3], R.mipmap.bd_dahaqi
        );
        allExpressionTable.put(page_2[4], R.mipmap.be_beishang
        );
        allExpressionTable.put(page_2[5], R.mipmap.bf_bizui
        );
        allExpressionTable.put(page_2[6], R.mipmap.bg_ding
        );
        allExpressionTable.put(page_2[7], R.mipmap.bh_ganmao
        );
        allExpressionTable.put(page_2[8], R.mipmap.bi_han
        );
        allExpressionTable.put(page_2[9], R.mipmap.bj_aini
        );
        allExpressionTable.put(page_2[10], R.mipmap.bk_heixian
        );
        allExpressionTable.put(page_2[11], R.mipmap.bl_heng
        );
        allExpressionTable.put(page_2[12], R.mipmap.bm_huaxin
        );
        allExpressionTable.put(page_2[13], R.mipmap.bn_kelian
        );
        allExpressionTable.put(page_2[14], R.mipmap.bo_ku
        );
        allExpressionTable.put(page_2[15], R.mipmap.bp_kun
        );
        allExpressionTable.put(page_2[16], R.mipmap.bq_landelini
        );
        allExpressionTable.put(page_2[17], R.mipmap.br_qian
        );
        allExpressionTable.put(page_2[18], R.mipmap.bs_shayan
        );
        allExpressionTable.put(page_2[19], R.mipmap.bt_shengbing
        );


        allExpressionTable.put(page_3[0], R.mipmap.ca_shiwang
        );
        allExpressionTable.put(page_3[1], R.mipmap.cb_shuijiao
        );
        allExpressionTable.put(page_3[2], R.mipmap.cc_zhuakuang
        );
        allExpressionTable.put(page_3[3], R.mipmap.cd_taikaixin
        );
        allExpressionTable.put(page_3[4], R.mipmap.ce_touxiao
        );
        allExpressionTable.put(page_3[5], R.mipmap.cf_tu
        );
        allExpressionTable.put(page_3[6], R.mipmap.cg_wabishi
        );
        allExpressionTable.put(page_3[7], R.mipmap.ch_weiqu
        );
        allExpressionTable.put(page_3[8], R.mipmap.ci_yun
        );
        allExpressionTable.put(page_3[9], R.mipmap.cj_shuai
        );
        allExpressionTable.put(page_3[10], R.mipmap.ck_doge
        );
        allExpressionTable.put(page_3[11], R.mipmap.cl_miao
        );
        allExpressionTable.put(page_3[12], R.mipmap.cm_xiongmao
        );
        allExpressionTable.put(page_3[13], R.mipmap.cn_tuzi
        );
        allExpressionTable.put(page_3[14], R.mipmap.co_zhutou
        );
        allExpressionTable.put(page_3[15], R.mipmap.cp_shenshou
        );
        allExpressionTable.put(page_3[16], R.mipmap.cq_nanhaier
        );
        allExpressionTable.put(page_3[17], R.mipmap.cr_nvhaier
        );
        allExpressionTable.put(page_3[18], R.mipmap.cs_feizao
        );
        allExpressionTable.put(page_3[19], R.mipmap.ct_aoteman

        );

        allExpressionTable.put(page_4[0], R.mipmap.da_geili
        );
        allExpressionTable.put(page_4[1], R.mipmap.db_jiong
        );
        allExpressionTable.put(page_4[2], R.mipmap.dc_meng
        );
        allExpressionTable.put(page_4[3], R.mipmap.dd_shenma
        );
        allExpressionTable.put(page_4[4], R.mipmap.de_v5
        );
        allExpressionTable.put(page_4[5], R.mipmap.df_xi
        );
        allExpressionTable.put(page_4[6], R.mipmap.dg_zhi
        );
        allExpressionTable.put(page_4[7], R.mipmap.dh_buyao
        );
        allExpressionTable.put(page_4[8], R.mipmap.di_good
        );
        allExpressionTable.put(page_4[9], R.mipmap.dj_haha
        );
        allExpressionTable.put(page_4[10], R.mipmap.dk_lai
        );
        allExpressionTable.put(page_4[11], R.mipmap.dl_ok
        );
        allExpressionTable.put(page_4[12], R.mipmap.dm_ruo
        );
        allExpressionTable.put(page_4[13], R.mipmap.dn_woshou
        );
        allExpressionTable.put(page_4[14], R.mipmap.do_ye
        );
        allExpressionTable.put(page_4[15], R.mipmap.dp_zan
        );
        allExpressionTable.put(page_4[16], R.mipmap.dq_zuoyi
        );
        allExpressionTable.put(page_4[17], R.mipmap.dr_shangxin
        );
        allExpressionTable.put(page_4[18], R.mipmap.ds_xin
        );
        allExpressionTable.put(page_4[19], R.mipmap.dt_dangao
        );


        allExpressionTable.put(page_5[0], R.mipmap.ea_feiji
        );
        allExpressionTable.put(page_5[1], R.mipmap.eb_ganbei
        );
        allExpressionTable.put(page_5[2], R.mipmap.ec_huatong
        );
        allExpressionTable.put(page_5[3], R.mipmap.ed_lazhu
        );
        allExpressionTable.put(page_5[4], R.mipmap.ee_liwu
        );
        allExpressionTable.put(page_5[5], R.mipmap.ef_lvsidai
        );
        allExpressionTable.put(page_5[6], R.mipmap.eg_weibo
        );
        allExpressionTable.put(page_5[7], R.mipmap.eh_weiguan
        );
        allExpressionTable.put(page_5[8], R.mipmap.ei_yinyue
        );
        allExpressionTable.put(page_5[9], R.mipmap.ej_zhaoxiangji
        );
        allExpressionTable.put(page_5[10], R.mipmap.ek_zhong
        );
        allExpressionTable.put(page_5[11], R.mipmap.el_weifeng
        );
        allExpressionTable.put(page_5[12], R.mipmap.em_xianhua
        );
        allExpressionTable.put(page_5[13], R.mipmap.en_taiyang
        );
        allExpressionTable.put(page_5[14], R.mipmap.eo_yueliang
        );
        allExpressionTable.put(page_5[15], R.mipmap.ep_fuyun
        );
        allExpressionTable.put(page_5[16], R.mipmap.eq_xiayu
        );
        allExpressionTable.put(page_5[17], R.mipmap.er_shachenbao
        );
    }

    /**
     * 最近使用表情缓存
     */
    private static String[] recentExpression = new String[21];
    /**
     * tab
     */
    private static String[] pageTitle;

    /**
     * 得到每类表情的标签，可拓展
     *
     * @return
     */
    public static String[] getPageTitle() {
        String category_1 = "最近";
        String category_2 = "表情";
        String category_3 = "表二";
        if (pageTitle == null) {
            pageTitle = new String[]{category_1, category_2, category_3};
        }
        return pageTitle;
    }

    public static String[] getRecentExpression() {
        return recentExpression;
    }

    public static HashMap<String, Integer> getAllExpressionTable() {
        return allExpressionTable;
    }
}