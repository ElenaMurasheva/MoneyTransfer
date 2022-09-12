package ru.netology.web.test;

import lombok.val;
import lombok.Value;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;

import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;
import static ru.netology.web.data.DataHelper.getSecondCardInfo;

class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {

        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashBoardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashBoardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashBoardPage.selectCardToTransfer(secondCardInfo);
        dashBoardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashBoardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashBoardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);


    }
}



