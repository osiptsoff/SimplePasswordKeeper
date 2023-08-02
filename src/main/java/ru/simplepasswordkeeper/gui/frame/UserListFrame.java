package ru.simplepasswordkeeper.gui.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.simplepasswordkeeper.api.dao.UserStorage;
import ru.simplepasswordkeeper.gui.component.ScrollableList;
import ru.simplepasswordkeeper.gui.toolbar.UserListFrameToolbar;

import javax.swing.JFrame;
import java.awt.BorderLayout;

@Component
public final class UserListFrame extends JFrame {
    private static final int CellHeight = 75;

    private final UserStorage userStorage;

    @Autowired
    public UserListFrame(UserListFrameToolbar toolbar,
                         @Qualifier("onDiskUserStorage") UserStorage userStorage,
                         ScrollableList<String> scrollableList) {
        this.userStorage = userStorage;
        this.add(toolbar, BorderLayout.NORTH);
        this.add(scrollableList, BorderLayout.CENTER);

        toolbar.setList(scrollableList);
        toolbar.setUserStorage(userStorage);

        for(var str : userStorage.getUsernames())
            scrollableList.addElement(str);

        scrollableList.setFixedCellHeight(CellHeight);
    }
}
