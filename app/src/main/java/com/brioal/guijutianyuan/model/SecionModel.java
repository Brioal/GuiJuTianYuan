package com.brioal.guijutianyuan.model;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Brioal on 2016/5/28.
 */

public class SecionModel extends SectionEntity<ReserveModel> {


    public SecionModel(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SecionModel(ReserveModel model) {
        super(model);
    }


}
