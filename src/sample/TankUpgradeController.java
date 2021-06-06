package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TankUpgradeController implements Initializable {
    public Text currentSpeed;
    public Text speedPrice;
    public Text currentAttack;
    public Text attackPrice;
    public Text currentHP;
    public Text hpPrice;
    public Button hpUpgradeButton;
    public Button attackUpgradeButton;
    public Button speedUpgradeButton;
    public Text currencyAmount;
    public Text aimPrice;
    public Text currentAimSpeed;
    public Button aimSpeedUpgradeButton;
    AudioClip spendMoneySound = new AudioClip(getClass().getResource("/sounds/spend_money_sound.mp3").toExternalForm());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }

    public void returnToGarage(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(Scenes.getGarageWindow(getClass()));
    }

    private void setData() {
        currencyAmount.setText(Money.getAmount() + "$");
        if (TankUpgradeData.getSpeedLevel() == 3) {
            speedUpgradeButton.setDisable(true);
            speedPrice.setText("Max lvl");
        } else {
            speedPrice.setText(TankUpgradeData.getCurrentSpeedUpgradePrice() + "$");
        }

        if (TankUpgradeData.getCurrentSpeedUpgradePrice() > Money.getAmount()) {
            speedUpgradeButton.setDisable(true);
        }

        if (TankUpgradeData.getAttackLevel() == 5) {
            attackUpgradeButton.setDisable(true);
            attackPrice.setText("Max lvl");
        } else {
            attackPrice.setText(TankUpgradeData.getCurrentAttackUpgradePrice() + "$");
        }

        if (TankUpgradeData.getCurrentAttackUpgradePrice() > Money.getAmount()) {
            attackUpgradeButton.setDisable(true);
        }

        if (TankUpgradeData.getHpLevel() == 5) {
            hpUpgradeButton.setDisable(true);
            hpPrice.setText("Max lvl");
        } else {
            hpPrice.setText(TankUpgradeData.getCurrentHPUpgradePrice() + "$");
        }

        if (TankUpgradeData.getCurrentHPUpgradePrice() > Money.getAmount()) {
            hpUpgradeButton.setDisable(true);
        }

        if (TankUpgradeData.getAimSpeedLevel() == 5) {
            aimSpeedUpgradeButton.setDisable(true);
            aimPrice.setText("Max lvl");
        } else {
            aimPrice.setText(TankUpgradeData.getCurrentAimSpeedUpgradePrice() + "$");
        }

        if (TankUpgradeData.getCurrentAimSpeedUpgradePrice() > Money.getAmount()) {
            aimSpeedUpgradeButton.setDisable(true);
        }
        currentSpeed.setText(String.valueOf(PlayerTank.maxSpeed));
        currentAttack.setText(String.valueOf(PlayerTank.attack));
        currentHP.setText(String.valueOf(PlayerTank.maxHP));
        currentAimSpeed.setText(String.valueOf(PlayerTank.aimSpeed));
    }

    public void upgradeSpeed(MouseEvent mouseEvent) {
        Money.decreaseAmount(TankUpgradeData.getCurrentSpeedUpgradePrice());
        TankUpgradeData.setSpeedLevel(TankUpgradeData.getSpeedLevel() + 1);
        PlayerTank.maxSpeed++;
        spendMoneySound.play();
        setData();
    }

    public void upgradeAttack(MouseEvent mouseEvent) {
        Money.decreaseAmount(TankUpgradeData.getCurrentAttackUpgradePrice());
        TankUpgradeData.setAttackLevel(TankUpgradeData.getAttackLevel() + 1);
        PlayerTank.attack += 10;
        spendMoneySound.play();
        setData();
    }

    public void upgradeHP(MouseEvent mouseEvent) {
        Money.decreaseAmount(TankUpgradeData.getCurrentHPUpgradePrice());
        TankUpgradeData.setHpLevel(TankUpgradeData.getHpLevel() + 1);
        PlayerTank.maxHP += 20;
        spendMoneySound.play();
        setData();
    }

    public void upgradeAimSpeed(MouseEvent mouseEvent) {
        Money.decreaseAmount(TankUpgradeData.getCurrentAimSpeedUpgradePrice());
        TankUpgradeData.setAimSpeedLevel(TankUpgradeData.getAimSpeedLevel() + 1);
        PlayerTank.aimSpeed++;
        spendMoneySound.play();
        setData();
    }
}
