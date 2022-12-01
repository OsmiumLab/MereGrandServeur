package fr.osmium.meregrand.Crypto.ElGamal;

import java.math.BigInteger;

public class SecretKey implements Key{
    private BigInteger x;

    public SecretKey(BigInteger x) {
        this.x = x;
    }

    public BigInteger getX() {
        return x;
    }

    @Override
    public String toString() {
        return "SecretKey{" +
                "x=" + x +
                '}';
    }
}