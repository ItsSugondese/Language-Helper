package org.example.screen.german.noun;

import lombok.Getter;
import lombok.Setter;
import org.example.MainFrame;
import org.example.screen.german.ScoreParent;

@Getter
@Setter
public class NounParent extends ScoreParent {

    public NounParent(MainFrame frame, int width, int height) {
        super(frame, width, height);
    }

}
