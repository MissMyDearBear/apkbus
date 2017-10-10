package com.apkbus.weather.utils;

import com.apkbus.weather.api.ApiCallBack;
import com.apkbus.weather.api.ApiHelper;
import com.apkbus.weather.base.MyApplication;
import com.apkbus.weather.db.CityBean;
import com.apkbus.weather.db.CityBeanDao;
import com.apkbus.weather.db.DistrictBean;
import com.apkbus.weather.db.DistrictBeanDao;
import com.apkbus.weather.db.ProvinceBean;
import com.apkbus.weather.entry.CityResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
public class CityUtils {
    public static final int LEVEL_PROVINCE=0;//省 直辖市
    public static final int LEVEL_CITY=1;//省 直辖市
    public static final int LEVEL_DISTRICT =2;//市区
    public static void getAllCities(){
        if(MyApplication.INSTANCE.getDaoSession().getProvinceBeanDao().queryBuilder().count()>0){
            return;
        }
        final HashMap<String,String>params=new HashMap<>();
        params.put("key", ApiHelper.KEY);
        ApiHelper.get("/weather/citys", params, new ApiCallBack() {
            @Override
            public void onSuccess(String result) {
                CityResult bean=GsonUtils.jsonToClass(result,CityResult.class);
                if(bean!=null&&bean.result!=null&&bean.result.size()>0){
                    List<ProvinceBean>provinceBeanLis=new ArrayList<>();
                    List<CityBean>cityBeanLis=new ArrayList<>();
                    List<DistrictBean>districtBeanBeanLis=new ArrayList<>();
                    for(CityResult.ResultBean resultBean:bean.result){
                        ProvinceBean province=new ProvinceBean();
                        province.provinceName=resultBean.province;
                        provinceBeanLis.add(province);
                        if(resultBean.city!=null&&resultBean.city.size()>0){
                            for(CityResult.ResultBean.CityBean cityBean:resultBean.city){
                                CityBean city=new CityBean();
                                city.cityName=cityBean.city;
                                city.provinceName=resultBean.province;
                                cityBeanLis.add(city);
                                if(cityBean.district!=null&&cityBean.district.size()>0){
                                    for(CityResult.ResultBean.CityBean.DistrictBean districtBean:cityBean.district){
                                        DistrictBean district=new DistrictBean();
                                        district.cityName=cityBean.city;
                                        district.districtName=districtBean.district;
                                        districtBeanBeanLis.add(district);
                                    }
                                }
                            }
                        }
                    }

                    MyApplication.INSTANCE.getDaoSession().getProvinceBeanDao().saveInTx(provinceBeanLis);
                    MyApplication.INSTANCE.getDaoSession().getCityBeanDao().saveInTx(cityBeanLis);
                    MyApplication.INSTANCE.getDaoSession().getDistrictBeanDao().saveInTx(districtBeanBeanLis);

                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
    public static <T> List<T>getListData(String key,int level){
        List<T>list=new ArrayList<>();
        switch (level){
            case LEVEL_PROVINCE:
                List<ProvinceBean> province = MyApplication.INSTANCE
                        .getDaoSession().getProvinceBeanDao().loadAll();
                list.add((T) province);
                break;
                case LEVEL_CITY:
                    List<CityBean> city = MyApplication.INSTANCE.getDaoSession()
                            .getCityBeanDao()
                            .queryBuilder().where(CityBeanDao.Properties.ProvinceName.eq(key)).build().list();
                list.addAll((Collection<? extends T>) city);
                break;
                case LEVEL_DISTRICT:
                    List<DistrictBean> district = MyApplication.INSTANCE.getDaoSession()
                            .getDistrictBeanDao()
                            .queryBuilder().where(DistrictBeanDao.Properties.CityName.eq(key)).build().list();
                    list.addAll((Collection<? extends T>) district);
                break;

        }
        return list;
    }
}
