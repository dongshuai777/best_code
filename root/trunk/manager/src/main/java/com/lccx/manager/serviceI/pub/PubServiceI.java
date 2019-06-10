package com.lccx.manager.serviceI.pub;

import com.lccx.manager.entity.SysFile;

import java.util.List;

public interface PubServiceI {


    void saveFile(SysFile data) throws Exception;

    List<SysFile> getFile(SysFile data);
}
