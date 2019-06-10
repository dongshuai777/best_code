package com.lccx.manager.dao.pub;

import com.lccx.manager.entity.SysFile;

import java.util.List;

public interface PubMapper {

    void saveFile(SysFile data) throws Exception;

    List<SysFile> queryFile(SysFile data);
}
