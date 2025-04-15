package org.example.screen.german.noun;

import lombok.Getter;
import lombok.Setter;
import org.example.MainFrame;
import org.example.enums.WordScreenType;
import org.example.global.parentclass.MaterialParent;

@Getter
@Setter
public class NounParent extends MaterialParent {

    public NounParent(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        super.initilizer();
    }

    @Override
    protected void materials() throws Exception {
        super.materials();
    }

    @Override
    protected WordScreenType getWordScreenType(){
        return  WordScreenType.GERMAN_NOUN;
    }
}
