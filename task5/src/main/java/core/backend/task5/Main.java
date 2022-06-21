package core.backend.task5;

import core.backend.task5.dao.ExchangeArguments;
import core.backend.task5.dao.UserAccountDao;
import core.backend.task5.dao.UserAccountDaoImpl;
import core.backend.task5.service.UserAccountService;
import core.backend.task5.service.UserAccountServiceImpl;
import core.backend.task5.util.ExchangeRatesUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        UserAccountDao userAccountDao = new UserAccountDaoImpl();
        UserAccountService userAccountService = new UserAccountServiceImpl(userAccountDao);

        ExchangeRatesUtil.createSampleAccounts(userAccountService);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Consumer<ExchangeArguments> consumer = userAccountService::exchange;

        List<ExchangeArguments> arguments = ExchangeRatesUtil.generateExchangeRates();

        for (ExchangeArguments exchangeArguments : arguments) {
            executorService.submit(new Task(consumer, exchangeArguments));
        }

        executorService.awaitTermination(4000, TimeUnit.MILLISECONDS);
        executorService.shutdown();

        System.out.println("User account 1 after exchanging: " + userAccountService.readUserAccount(1));
        System.out.println("User account 2 after exchanging: " + userAccountService.readUserAccount(2));
    }
}
