package com.lccx.manager.service.pub;

import com.lccx.manager.dao.pub.PubMapper;
import com.lccx.manager.entity.SysFile;
import com.lccx.manager.serviceI.pub.PubServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PubServiceImpl implements PubServiceI {


    @Resource
    private PubMapper mapper;


    /**
     * 保存文件记录到数据库
     * @param data
     * @throws Exception
     */
    @Override
    public void saveFile(SysFile data) throws Exception {
        mapper.saveFile(data);
    }


    /**
     * 查询文件
     * @param data
     * @return
     */
    @Override
    public List<SysFile> getFile(SysFile data) {
        return mapper.queryFile(data);
    }
}
