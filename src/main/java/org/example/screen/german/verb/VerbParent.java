package org.example.screen.german.verb;

import lombok.Getter;
import lombok.Setter;
import org.example.MainFrame;
import org.example.global.parentclass.MaterialParent;

@Getter
@Setter
public class VerbParent extends MaterialParent {

    public VerbParent(MainFrame frame, int width, int height) {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        super.initilizer();
    }

    @Override
    protected void materials() {
        super.materials();
    }
}
