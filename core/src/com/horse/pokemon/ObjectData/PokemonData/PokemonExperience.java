package com.horse.pokemon.ObjectData.PokemonData;

import gnu.trove.list.array.TIntArrayList;

/**
 * Experience Object for PokemonData, allowing for getting a PokemonData's
 * current level based on their <code>currentExperience</code>.
 *
 * @author Horse
 * @version 1.0
 * @since 1.0
 */
public class PokemonExperience {
    /**
     * Changing this value will set all PokemonData to start at the new value.
     * Value - {@value}, the experience value all PokemonData start with.
     */
    private static final int DEFAULT_EXPERIENCE = 0;
    
    private static final TIntArrayList FLUCTUATING_TOTAL_EXPERIENCE_PER_LEVEL =
            new TIntArrayList(new int[] {0, 4, 13, 32, 65, 112, 178, 276, 393, 540, 745, 967, 1230, 1591, 1957, 2457, 3046, 3732, 4526, 5440, 6482, 7666, 9003, 10506, 12187});
    /**
     * Experience value that is altered each time a PokemonData gains experience.
     */
    private int currentExperience;
    /**
     * PokemonData's experience type based on {@link Pokemon}.
     */
    private ExperienceTypes experienceType;
    
    /**
     * Class Constructor.
     * {@code this.currentExperience} is set to 0 at initialization so each PokemonData using
     * {@link PokemonExperience} will start at the smallest possible value in terms of experience points.
     *
     * @param experienceType to add variety to the amount of experience required for each PokemonData. Declared at {@link Pokemon}
     */
    public PokemonExperience(ExperienceTypes experienceType) {
        setCurrentExperience(getDefaultExperience());
        setExperienceType(experienceType);
    }
    
    public static TIntArrayList getFluctuatingTotalExperiencePerLevel() {
        return FLUCTUATING_TOTAL_EXPERIENCE_PER_LEVEL;
    }
    
    public static int getDefaultExperience() {
        return DEFAULT_EXPERIENCE;
    }
    
    public static void main(String[] args) {
        PokemonExperience pokemonExperience = new PokemonExperience(ExperienceTypes.MEDIUM_SLOW);
        long              start             = System.nanoTime();
        System.out.println(pokemonExperience.getLevel());
        System.out.println(System.nanoTime() - start);
    }
    
    public int getCurrentExperience(boolean basedOnLevel) {
        if(basedOnLevel) {
            if(experienceType == ExperienceTypes.FLUCTUATING) {
                return getFluctuatingTotalExperiencePerLevel().get(getLevel() - 1);
            } else if(experienceType == ExperienceTypes.SLOW) {
                return (5 * getLevel() * getLevel() * getLevel() / 4);
            } else if(experienceType == ExperienceTypes.MEDIUM_SLOW) {
                return (int)((1.2 * getLevel() * getLevel() * getLevel() - (15 * getLevel() * getLevel() + (100 * getLevel()) - 140)));
            } else if(experienceType == ExperienceTypes.MEDIUM_FAST) {
                return getLevel() * getLevel() * getLevel();
            } else if(experienceType == ExperienceTypes.FAST) {
                return (4 * getLevel() * getLevel() * getLevel()) / 5;
            } else if(experienceType == ExperienceTypes.ERRATIC) {
                return 0;
            } else {
                return currentExperience;
            }
        } else {
            return currentExperience;
        }
    }
    
    /**
     * Method for finding the current level of PokemonData that contain the {@code PokemonExperience} object.<p> The
     * Switch-Case returns the PokemonData's level based on its {@code experienceTypes} and {@code currentExperience}.
     * All return statements are casted so that all the level turnouts are truncated.  The default case should not be
     * called at any time, otherwise the {@code getLevel} will throw a new {@code IllegalStateException}.
     * Formula for finding <code>MediumSlow</code> and credited to:
     *
     * @return Number for what level a PokemonData is at after an experience change.
     *
     * @see <a href="http://www.math.vanderbilt.edu/~schectex/courses/cubic/">The Cubic Formula/</a>
     * @see Math Package that helped with all the formulas.
     */
    public int getLevel() {
        if(experienceType == ExperienceTypes.FLUCTUATING) {
            return 1;
        } else if(experienceType == ExperienceTypes.SLOW) {
            return (int)(Math.cbrt(0.8 * currentExperience));
        } else if(experienceType == ExperienceTypes.MEDIUM_SLOW) {
            if(getCurrentExperience() < 5) {
                return 1;
            } else {
                return 1;
            }
            //return (int)((Math.cbrt(((-3375 / 46.656) + (-1500 / (6 * 1.44)) - (-140 / (2 * 1.2))) + Math.sqrt(
            //        Math.pow(-Math.pow(-15, 3) + ((-15 * 100) / (Math.pow(6 * 1.2, 2)) - (-140 / (2 * 1.2))), 2) + (100 / (3 * 1.2) - (Math.pow(-15, 2) / (9 * Math.pow(1.2, 2)))))) +
            //              Math.cbrt((((-Math.pow(-15, 3)) / (27 * Math.pow(1.2, 3))) + ((-15 * 100) / (6 * Math.pow(1.2, 2))) - (-140 / (2 * 1.2))) - Math.sqrt(
            //                      Math.pow(-Math.pow(-15, 3) + ((-15 * 100) / (Math.pow(6 * 1.2, 2)) - (-140 / (2 * 1.2))), 2) +
            //                      (100 / (3 * 1.2) - (Math.pow(-15, 2) / (9 * Math.pow(1.2, 2))))))) - (-15 / (3 * 1.2)));
        } else if(experienceType == ExperienceTypes.MEDIUM_FAST) {
            return (int)(Math.cbrt(currentExperience));
        } else if(experienceType == ExperienceTypes.FAST) {
            return (int)(Math.cbrt(1.25 * currentExperience));
        } else if(experienceType == ExperienceTypes.ERRATIC) {
            return 1;
        } else {
            return 1;
        }
    }
    
    @Override
    public String toString() {
        return String.format("{Experience Type = %s, Experience Value = %s, Level = %s}", experienceType, currentExperience, getLevel());
    }
    
    public int getCurrentExperience() {
        return currentExperience;
    }
    
    public void setCurrentExperience(int currentExperience) {
        this.currentExperience = currentExperience;
    }
    
    public ExperienceTypes getExperienceType() {
        return experienceType;
    }
    
    public void setExperienceType(ExperienceTypes experienceType) {
        this.experienceType = experienceType;
    }
}