package com.javarush.task.task37.task3709.connectors;

import com.javarush.task.task37.task3709.security.SecurityChecker;
import com.javarush.task.task37.task3709.security.SecurityCheckerImpl;

public class SecurityProxyConnector implements Connector {

    SimpleConnector connector;
    SecurityChecker checker;

    public SecurityProxyConnector(String resourceString) {
        this.connector = new SimpleConnector(resourceString);
        this.checker = new SecurityCheckerImpl();
    }

    @Override
    public void connect() {
        if (checker.performSecurityCheck()) {
            connector.connect();
        }
    }
}
