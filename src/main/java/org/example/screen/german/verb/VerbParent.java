package org.example.screen.german.verb;

import lombok.Getter;
import lombok.Setter;
import org.example.MainFrame;
import org.example.screen.german.ScoreParent;

@Getter
@Setter
public class VerbParent extends ScoreParent {

    public VerbParent(MainFrame frame, int width, int height) {
        super(frame, width, height);
    }

}
