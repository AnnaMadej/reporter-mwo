package App;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

/**
 * Created by Mateusz Sutor on 26/06/2020, 10:09
 */
public class UserControlTest {

    String folderPath = "src/test/testing-data/reporter-dane";

    @Before
    public void setUp() throws Exception {
    }
    UserControl userControl = new UserControl(folderPath);

    @Test
    public void controlLoop() {
    }

    @Test
    public void showOption() {
        StringBuilder sb = new StringBuilder();
            sb.append("WYBIERZ OPCJE:\n");
            sb.append("1. Generuj raport godzin pracowników w podanym roku\n");
            sb.append("2. Generuj raport godzin projektów w podanym roku\n");
            sb.append("3. Generuj raport godzin przepracowanych miesięcznie przez pracownika w podanym roku\n");
            sb.append("4. Generuj procentowy udział projektów w pracy osob dla podanego roku\n");
            sb.append("5. Generuj raport ilości godzin pracowników w podanym projekcie\n");
            sb.append("6. Wyświetl wykres słupkowy godzin projektów w podanym roku\n");
            sb.append("7. Wyświetl wykres kołowy procentowego udziału projektów dla pracowników w podanym roku\n");
            sb.append("8. Pokaż logi z odczytu pliku\n");
            sb.append("0. Zakończ pracę z programem\n");

            Assert.assertEquals(sb.toString(), userControl.showOptions());
    }

}