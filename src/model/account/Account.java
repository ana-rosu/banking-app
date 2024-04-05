package model.account;

import model.card.Card;
import model.transaction.Transaction;
import interfaces.Transactionable;

import java.util.List;
import java.util.Random;

public abstract class Account implements Transactionable {
    protected int id;
    static int contorId = 1;
    protected String IBAN;
    protected double balance;
    protected List<Transaction> transactionHistory;
    protected Card linkedCard;
    protected AccountStatus accountStatus;

    {
        this.id = contorId++;
    }
    public Account(double balance) {
        this.IBAN = generateIban();
        this.balance = balance;
        this.accountStatus = AccountStatus.OPEN;
    }

    @Override
    public void deposit(double amount) {
        if(amount > 0){
            setBalance(balance + amount);
        }
    }
    @Override
    public boolean withdraw(double amount) {
        if(amount > 0 && amount > balance){
            System.out.println("Insufficient funds.");
            return false;
        }
        this.balance -= amount;
        return true;
    }

    @Override
    public void transfer(Account destination, double amount) {
        if (amount > 0 && this.withdraw(amount)) {
            destination.deposit(amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed. Insufficient funds.");
        }
    }

    private String generateIban() {
        String countryCode = "RO";
        String bankCode = "BNK";
        String accountNumber = generateRandom10DigitsNumber();
        return countryCode + bankCode + accountNumber;
    }
    private String generateRandom10DigitsNumber(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    public int getId() {
        return id;
    }
    public double getBalance() {
        return balance;
    }
    public String getIBAN() {
        return IBAN;
    }
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
    public Card getLinkedCard() {
        return linkedCard;
    }
    public void setLinkedCard(Card linkedCard) {
        this.linkedCard = linkedCard;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
    public void addTransaction(Transaction transaction){
        this.transactionHistory.add(transaction);
    }
}
