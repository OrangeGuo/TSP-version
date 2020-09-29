package frontends;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;

@RequiredArgsConstructor
public enum Icons {
    ProgramIcon("src/main/resources/img/p1.jpg"),
    WorkIcon("src/main/resources/img/p2.jpg"),
    SettingIcon("src/main/resources/img/p3.jpg"),
    HelpIcon("src/main/resources/img/p4.jpg"),
    TutorialIcon("src/main/resources/img/p5.jpg")
    ;
    private final String iconPath;

    public Image getImage(){
        return new ImageIcon(iconPath).getImage();
    }
}
