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
    
    private static final TIntArrayList TOTAL_EXPERIENCE_PER_LEVEL = new TIntArrayList(new int[] {
            0, 4, 13, 32, 65, 112, 178, 276, 393, 540, 745, 967, 1230, 1591, 1957, 2457, 3046, 3732, 4526, 5440, 6482, 7666, 9003, 10506, 12187, 14060, 16140, 18439, 20974, 23760, 26811,
            30146, 33780, 37731, 42017, 46656, 50653, 55969, 60505, 66560, 71677, 78533, 84277, 91998, 98415, 107069, 114205, 123863, 131766, 142500, 151222, 163105, 172697, 185807, 196322,
            210739, 222231, 238036, 250562, 267840, 281456, 300293, 315059, 335544, 251520, 373744, 390991, 415050, 433631, 459620, 479600, 507617, 529063, 559209, 582187, 614566, 639146,
            673863, 700115, 737280, 765275, 804997, 834809, 877201, 908905, 954084, 987754, 1035837, 1071552, 1122660, 1160499, 1214753, 1254796, 1312322, 1354652, 1415577, 1460276, 1524731,
            1571884, 1640000,
            
            0, 10, 33, 80, 156, 270, 428, 640, 911, 1250, 1663, 2160, 2746, 3430, 4218, 5120, 6141, 7290, 8573, 10000, 11576, 13310, 15208, 17280, 19531, 17280, 19531, 21970, 24603, 27440,
            30486, 33750, 37238, 40960, 44921, 49130, 53593, 58320, 63316, 68590, 74148, 80000, 86151, 92610, 99383, 106480, 113906, 121670, 129778, 138240, 147061, 156250, 165813, 175760,
            186096, 196830, 207968, 219520, 231491, 243890, 256723, 270000, 283726, 297910, 312558, 327680, 343281, 359370, 375953, 393040, 410636, 428750, 447388, 466560, 486271, 506530,
            527343, 548720, 570666, 593190, 616298, 640000, 664301, 689210, 714733, 740880, 767656, 795070, 823128, 851840, 881211, 911250, 941953, 973360, 1005446, 1038230, 1071718,
            1105920, 1140841, 1176490, 1212873, 1250000
    });
    
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
    
    public static TIntArrayList getTotalExperiencePerLevel() {
        return TOTAL_EXPERIENCE_PER_LEVEL;
    }
    
    public static int getDefaultExperience() {
        return DEFAULT_EXPERIENCE;
    }
    
    public static void main(String[] args) {
        PokemonExperience pokemonExperience = new PokemonExperience(ExperienceTypes.MEDIUM_SLOW);
        long              start             = System.nanoTime();
        System.out.println(pokemonExperience.getLevel());
        System.out.println(System.nanoTime() - start);
    
        pokemonExperience.setExperienceType(ExperienceTypes.SLOW);
        for(int index = 0; index < 100; index++) {
            pokemonExperience.setCurrentExperience(getTotalExperiencePerLevel().get(index + 100));
            System.out.println(pokemonExperience.getLevel());
        }
    }
    
    public int getCurrentExperience(boolean basedOnLevel) {
        if(basedOnLevel) {
            if(experienceType == ExperienceTypes.FLUCTUATING) {
                return getTotalExperiencePerLevel().get(getLevel() > 100 ? 99 : getLevel() - 1);
            } else if(experienceType == ExperienceTypes.SLOW) {
                return getTotalExperiencePerLevel().get(getLevel() > 100 ? 199 : getLevel() + 99);
            } else if(experienceType == ExperienceTypes.MEDIUM_SLOW) {
                return (int)((1.2 * getLevel() * getLevel() * getLevel() - (15 * getLevel() * getLevel() + (100 * getLevel()) - 140)));
            } else if(experienceType == ExperienceTypes.MEDIUM_FAST) {
                return getLevel() * getLevel() * getLevel();
            } else if(experienceType == ExperienceTypes.FAST) {
                return (4 * getLevel() * getLevel() * getLevel()) / 5;
            } else if(experienceType == ExperienceTypes.ERRATIC) {
                return 0;
            } else {
                return getCurrentExperience();
            }
        } else {
            return getCurrentExperience();
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
        int[] experienceValues;
        if(getExperienceType() == ExperienceTypes.FLUCTUATING) {
            experienceValues = getTotalExperiencePerLevel().toArray(0, 100);
        } else if(experienceType == ExperienceTypes.SLOW) {
            experienceValues = getTotalExperiencePerLevel().toArray(100, 100);
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
        for(int index = 0; index < experienceValues.length; index++) {
            if(getCurrentExperience() < experienceValues[index]) {
                return index;
            }
        }
        return 100;
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