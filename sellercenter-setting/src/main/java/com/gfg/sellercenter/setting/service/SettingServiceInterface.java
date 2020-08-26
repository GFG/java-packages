package com.gfg.sellercenter.setting.service;

import com.gfg.sellercenter.setting.entity.Setting;

/**
 * @author Dennis Munchausen <dennis.munchausen@global-fashion-group.com>
 * @version 1.0
 * @since 1.0
 */
public interface SettingServiceInterface {

    /**
     * This Setting object represent the actual setting value either on a global or on a seller level
     *
     * @param field Name of the field in the database (seller setting which override global)
     * @param sellerId Value of the seller field in the database for which you are looking for
     * @return Setting It retrieves a setting object with main important values for a setting
     * @throws Exception throws an exception when the endpoint is not reachable or no setting were found
     */
    Setting getSetting(String field, int sellerId) throws Exception;

    /**
     * This Setting object represent the actual setting value only on a global level
     *
     * @param field Description name of the field in the database (global setting)
     * @return Setting It retrieves a setting object with main important values for a setting
     * @throws Exception throws an exception when the endpoint is not reachable or no setting were found
     */
    Setting getSetting(String field) throws Exception;
}
