package com.horse.pokemon.amethyst.data.pokemon;

public class PartyPokemon {
    private Pokemon primaryPokemon;
    private Pokemon secondaryPokemon;
    private Pokemon tertiaryPokemon;
    private Pokemon quaternaryPokemon;
    private Pokemon quinaryPokemon;
    private Pokemon senaryPokemon;
    private Pokemon septenaryPokemon;
    private Pokemon octonaryPokemon;
    private Pokemon nonaryPokemon;
    private Pokemon denaryPokemon;
    
    public PartyPokemon() {
        this(null, null, null, null, null, null, null, null, null, null);
    }
    
    public PartyPokemon(final Pokemon primaryPokemon, final Pokemon secondaryPokemon, final Pokemon tertiaryPokemon,
                        final Pokemon quaternaryPokemon, final Pokemon quinaryPokemon, final Pokemon senaryPokemon,
                        final Pokemon septenaryPokemon, final Pokemon octonaryPokemon, final Pokemon nonaryPokemon,
                        final Pokemon denaryPokemon) {
        setPrimaryPokemon(primaryPokemon);
        setSecondaryPokemon(secondaryPokemon);
        setTertiaryPokemon(tertiaryPokemon);
        setQuaternaryPokemon(quaternaryPokemon);
        setQuinaryPokemon(quinaryPokemon);
        setSenaryPokemon(senaryPokemon);
        setSeptenaryPokemon(septenaryPokemon);
        setOctonaryPokemon(octonaryPokemon);
        setNonaryPokemon(nonaryPokemon);
        setDenaryPokemon(denaryPokemon);
    }
    
    public void swapPokemon(final Pokemon pokemonToRemove, final Pokemon newPokemon) {
        if(pokemonToRemove == getPrimaryPokemon()) {
            setPrimaryPokemon(newPokemon);
        } else if(pokemonToRemove == getSecondaryPokemon()) {
            setSecondaryPokemon(newPokemon);
        } else if(pokemonToRemove == getTertiaryPokemon()) {
            setTertiaryPokemon(newPokemon);
        } else if(pokemonToRemove == getQuaternaryPokemon()) {
            setQuaternaryPokemon(newPokemon);
        } else if(pokemonToRemove == getQuinaryPokemon()) {
            setQuinaryPokemon(newPokemon);
        } else if(pokemonToRemove == getSenaryPokemon()) {
            setSenaryPokemon(newPokemon);
        } else if(pokemonToRemove == getSeptenaryPokemon()) {
            setSeptenaryPokemon(newPokemon);
        } else if(pokemonToRemove == getOctonaryPokemon()) {
            setOctonaryPokemon(newPokemon);
        } else if(pokemonToRemove == getNonaryPokemon()) {
            setNonaryPokemon(newPokemon);
        } else if(pokemonToRemove == getDenaryPokemon()) {
            setDenaryPokemon(newPokemon);
        }
    }
    
    public boolean appendToPartyPokemon(final Pokemon newPokemon) {
        if(getPrimaryPokemon() == null) {
            setPrimaryPokemon(newPokemon);
            return true;
        } else if(getSecondaryPokemon() == null) {
            setSecondaryPokemon(newPokemon);
            return true;
        } else if(getTertiaryPokemon() == null) {
            setTertiaryPokemon(newPokemon);
            return true;
        } else if(getQuaternaryPokemon() == null) {
            setQuaternaryPokemon(newPokemon);
            return true;
        } else if(getQuinaryPokemon() == null) {
            setQuinaryPokemon(newPokemon);
            return true;
        } else if(getSenaryPokemon() == null) {
            setSenaryPokemon(newPokemon);
            return true;
        } else if(getSeptenaryPokemon() == null) {
            setSeptenaryPokemon(newPokemon);
            return true;
        } else if(getOctonaryPokemon() == null) {
            setOctonaryPokemon(newPokemon);
            return true;
        } else if(getNonaryPokemon() == null) {
            setNonaryPokemon(newPokemon);
            return true;
        } else if(getDenaryPokemon() == null) {
            setDenaryPokemon(newPokemon);
            return true;
        } else {
            return false;
        }
    }
    
    public Pokemon getPrimaryPokemon() {
        return primaryPokemon;
    }
    
    public void setPrimaryPokemon(Pokemon primaryPokemon) {
        this.primaryPokemon = primaryPokemon;
    }
    
    public Pokemon getSecondaryPokemon() {
        return secondaryPokemon;
    }
    
    public void setSecondaryPokemon(Pokemon secondaryPokemon) {
        this.secondaryPokemon = secondaryPokemon;
    }
    
    public Pokemon getTertiaryPokemon() {
        return tertiaryPokemon;
    }
    
    public void setTertiaryPokemon(Pokemon tertiaryPokemon) {
        this.tertiaryPokemon = tertiaryPokemon;
    }
    
    public Pokemon getQuaternaryPokemon() {
        return quaternaryPokemon;
    }
    
    public void setQuaternaryPokemon(Pokemon quaternaryPokemon) {
        this.quaternaryPokemon = quaternaryPokemon;
    }
    
    public Pokemon getQuinaryPokemon() {
        return quinaryPokemon;
    }
    
    public void setQuinaryPokemon(Pokemon quinaryPokemon) {
        this.quinaryPokemon = quinaryPokemon;
    }
    
    public Pokemon getSenaryPokemon() {
        return senaryPokemon;
    }
    
    public void setSenaryPokemon(Pokemon senaryPokemon) {
        this.senaryPokemon = senaryPokemon;
    }
    
    public Pokemon getSeptenaryPokemon() {
        return septenaryPokemon;
    }
    
    public void setSeptenaryPokemon(Pokemon septenaryPokemon) {
        this.septenaryPokemon = septenaryPokemon;
    }
    
    public Pokemon getOctonaryPokemon() {
        return octonaryPokemon;
    }
    
    public void setOctonaryPokemon(Pokemon octonaryPokemon) {
        this.octonaryPokemon = octonaryPokemon;
    }
    
    public Pokemon getNonaryPokemon() {
        return nonaryPokemon;
    }
    
    public void setNonaryPokemon(Pokemon nonaryPokemon) {
        this.nonaryPokemon = nonaryPokemon;
    }
    
    public Pokemon getDenaryPokemon() {
        return denaryPokemon;
    }
    
    public void setDenaryPokemon(Pokemon denaryPokemon) {
        this.denaryPokemon = denaryPokemon;
    }
}
