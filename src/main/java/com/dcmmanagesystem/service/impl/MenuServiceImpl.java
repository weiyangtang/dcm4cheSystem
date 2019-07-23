package com.dcmmanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcmmanagesystem.dao.MenuMapper;
import com.dcmmanagesystem.model.Menu;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.model.common.BootstrapTree;
import com.dcmmanagesystem.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-06-24
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    /***
     * 动态获取菜单（管理员和普通用户不同）
     * @param role
     * @return
     */
    @Override
    public BootstrapTree getBootstrapTree(String role) {
        ArrayList<BootstrapTree> bootstrapTrees = new ArrayList<>();
        if (role.contentEquals("admin")) {
            List<Menu> parentMenus = baseMapper.getAdminMenusByPid(0);
            List<BootstrapTree> childBootstrapTrees = new ArrayList<>();
            for (Menu parentMenu : parentMenus) {
                List<Menu> childMenus = baseMapper.getAdminMenusByPid(parentMenu.getId());
                for (Menu childMenu : childMenus) {
                    childBootstrapTrees.add(Menu2BoostrapTree(childMenu, null));
                }
                BootstrapTree bootstrapTree = Menu2BoostrapTree(parentMenu, childBootstrapTrees);
                bootstrapTrees.add(bootstrapTree);
//                childBootstrapTrees.clear();
                childBootstrapTrees = new ArrayList<>();
            }

        } else {
            List<Menu> parentMenus = baseMapper.getUserMenusByPid(0);
            List<BootstrapTree> childBootstrapTrees = new ArrayList<>();
            for (Menu parentMenu : parentMenus) {
                List<Menu> childMenus = baseMapper.getUserMenusByPid(parentMenu.getId());
                for (Menu childMenu : childMenus) {
                    childBootstrapTrees.add(Menu2BoostrapTree(childMenu, null));
                }
                bootstrapTrees.add(Menu2BoostrapTree(parentMenu, childBootstrapTrees));
//                childBootstrapTrees.clear();
                childBootstrapTrees = new ArrayList<>();
            }
        }
        return new BootstrapTree("菜单", "fa fa-home", "", "-1", "###", 0, bootstrapTrees);
//        return bootstrapTrees;
    }

    /***
     * menu对象转成BootstrapTree
     * @param menu
     * @param nodes
     * @return
     */
    private BootstrapTree Menu2BoostrapTree(Menu menu, List<BootstrapTree> nodes) {
        if (menu != null) {
//            System.out.println("子节点2:"+JSONUtil.toJsonPrettyStr(nodes));
            BootstrapTree bootstrapTree = new BootstrapTree(menu.getName(), menu.getIcon(), menu.getId().toString(), menu.getUrl(), menu.getIsBlank(), nodes);
            return bootstrapTree;
        }
        return null;
    }

}
