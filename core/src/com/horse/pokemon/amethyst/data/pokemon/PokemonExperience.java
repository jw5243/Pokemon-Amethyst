package com.horse.pokemon.amethyst.data.pokemon;

import com.horse.pokemon.amethyst.data.handlers.readers.PokemonDataReader;

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
    
    private static final int[] TOTAL_EXPERIENCE_PER_LEVEL = new int[] {
        //Experience values for fluctating.
        0, 4, 13, 32, 65, 112, 178, 276, 393, 540, 745, 967, 1230, 1591, 1957, 2457, 3046, 3732, 4526, 5440, 6482, 7666,
        9003, 10506, 12187, 14060, 16140, 18439, 20974, 23760, 26811, 30146, 33780, 37731, 42017, 46656, 50653, 55969,
        60505, 66560, 71677, 78533, 84277, 91998, 98415, 107069, 114205, 123863, 131766, 142500, 151222, 163105, 172697,
        185807, 196322, 210739, 222231, 238036, 250562, 267840, 281456, 300293, 315059, 335544, 251520, 373744, 390991,
        415050, 433631, 459620, 479600, 507617, 529063, 559209, 582187, 614566, 639146, 673863, 700115, 737280, 765275,
        804997, 834809, 877201, 908905, 954084, 987754, 1035837, 1071552, 1122660, 1160499, 1214753, 1254796, 1312322,
        1354652, 1415577, 1460276, 1524731, 1571884, 1640000,
    
        //Experience values for slow.
        0, 10, 33, 80, 156, 270, 428, 640, 911, 1250, 1663, 2160, 2746, 3430, 4218, 5120, 6141, 7290, 8573, 10000,
        11576, 13310, 15208, 17280, 19531, 21970, 24603, 27440, 30486, 33750, 37238, 40960, 44921, 49130, 53593, 58320,
        63316, 68590, 74148, 80000, 86151, 92610, 99383, 106480, 113906, 121670, 129778, 138240, 147061, 156250, 165813,
        175760, 186096, 196830, 207968, 219520, 231491, 243890, 256723, 270000, 283726, 297910, 312558, 327680, 343281,
        359370, 375953, 393040, 410636, 428750, 447388, 466560, 486271, 506530, 527343, 548720, 570666, 593190, 616298,
        640000, 664301, 689210, 714733, 740880, 767656, 795070, 823128, 851840, 881211, 911250, 941953, 973360, 1005446,
        1038230, 1071718, 1105920, 1140841, 1176490, 1212873, 1250000,
    
        //Experience values for medium slow.
        0, 9, 57, 96, 135, 179, 236, 314, 419, 560, 742, 973, 1261, 1612, 2035, 2535, 3120, 3798, 4575, 5460, 6458,
        7577, 8825, 10208, 11735, 13411, 15244, 17242, 19411, 21760, 24294, 27021, 29949, 33084, 36435, 40007, 43808,
        47846, 52127, 56660, 61450, 66505, 71833, 77440, 83335, 89523, 96012, 102810, 109923, 117360, 125126, 133229,
        141677, 150476, 159635, 169159, 179056, 189334, 199999, 211060, 222522, 234393, 246681, 259392, 272535, 286115,
        300140, 314618, 329555, 344960, 360838, 377197, 394045, 411388, 429235, 447591, 466464, 485862, 505791, 526260,
        547274, 568841, 590969, 613664, 636935, 660787, 685228, 710266, 735907, 762160, 789030, 816525, 844653, 873420,
        902835, 932903, 963632, 995030, 1027103, 1059860,
    
        //Experience values for medium fast.
        0, 8, 27, 64, 125, 216, 343, 512, 729, 1000, 1331, 1728, 2197, 2744, 3375, 4096, 4913, 5832, 6859, 8000, 9261,
        10648, 12167, 13824, 15625, 17576, 19683, 21952, 24389, 27000, 29791, 32768, 35937, 39304, 42875, 46656, 50653,
        54872, 59319, 64000, 68921, 74088, 79507, 85184, 91125, 97336, 103823, 110592, 117649, 125000, 132651, 140608,
        148877, 157464, 166375, 175616, 185193, 195112, 205379, 216000, 226981, 238328, 250047, 262144, 274625, 287496,
        300763, 314432, 328509, 343000, 357911, 373248, 389017, 405224, 421875, 438976, 456533, 474552, 493039, 512000,
        531441, 551368, 571787, 592704, 614125, 636056, 658503, 681472, 704969, 729000, 753571, 778688, 804357, 830584,
        857375, 884736, 912673, 941192, 970299, 1000000,
    
        //Experience values for fast.
        0, 6, 21, 51, 100, 172, 274, 409, 583, 800, 1064, 1382, 1757, 2195, 2700, 3276, 3930, 4665, 5487, 6400, 7408,
        8518, 9733, 11059, 12500, 14060, 15746, 17561, 19511, 21600, 23832, 26214, 28749, 31443, 34300, 37324, 40522,
        43897, 47455, 51200, 55136, 59270, 63605, 68147, 72900, 77868, 83058, 88473, 94119, 100000, 106120, 112486,
        119101, 125971, 133100, 140492, 148154, 156089, 164303, 172800, 181584, 190662, 200037, 209715, 219700, 229996,
        240610, 251545, 262807, 274400, 286328, 298598, 311213, 324179, 337500, 351180, 365226, 379641, 394431, 409600,
        425152, 441094, 457429, 474163, 491300, 508844, 526802, 545177, 563975, 583200, 602856, 622950, 643485, 664467,
        685900, 707788, 730138, 752953, 776239, 800000,
    
        //Experience values for erratic.
        0, 15, 52, 122, 237, 406, 637, 942, 1326, 1800, 2369, 3041, 3822, 4719, 5737, 6881, 8155, 9564, 11111, 12800,
        14632, 16610, 18737, 21012, 23437, 26012, 28737, 31610, 34632, 37800, 41111, 44564, 48155, 51881, 55737, 59719,
        63822, 68041, 72369, 76800, 81326, 85942, 90637, 95406, 100237, 105122, 110052, 115015, 120001, 125000, 131324,
        137795, 144410, 151165, 158056, 165079, 172229, 179503, 186894, 194400, 202013, 209728, 217540, 225443, 233431,
        241496, 249633, 257834, 267406, 276458, 286328, 296358, 305767, 316074, 326531, 336255, 346965, 357812, 367807,
        378880, 390077, 400293, 411686, 423190, 433572, 445239, 457001, 467489, 479378, 491346, 501878, 513934, 526049,
        536557, 548720, 560922, 571333, 583539, 591882, 600000
    };
    
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
    
    public static int[] getTotalExperiencePerLevel() {
        return TOTAL_EXPERIENCE_PER_LEVEL;
    }
    
    public static int getDefaultExperience() {
        return DEFAULT_EXPERIENCE;
    }
    
    public static void main(String[] args) {
        PokemonExperience pokemonExperience = new PokemonExperience(ExperienceTypes.FLUCTUATING);
        long              start             = System.nanoTime();
        pokemonExperience.getLevel();
        long end = System.nanoTime();
        System.out.println(end - start);
        
        for(ExperienceTypes experienceType : ExperienceTypes.values()) {
            System.out.println(
                String.format("\n~~~~~~~~~~~~~~~~~~~~~~~~ %s ~~~~~~~~~~~~~~~~~~~~~~~~\n", experienceType.name()));
            for(int index = 1; index <= 100; index++) {
                System.out.println(String.format("Experience for Level %s%s = %s", index,
                                                 (index < 10) ? "  " : (index < 100) ? " " : "",
                                                 getExperienceBasedOnLevelAndType(experienceType, index)
                ));
            }
        }
    
        long iterations = 100000000;
        long sum        = 0;
        for(int i = 0; i < iterations; i++) {
            long start1 = System.nanoTime();
            calculateExperienceWon(PokemonDataReader.getPokemon("Bulbasaur"), PokemonDataReader.getPokemon("Ivysaur"));
            long end1 = System.nanoTime();
            sum += end1 - start1;
        }
    
        System.out.println(sum / iterations);
    }
    
    public static int getExperienceBasedOnLevelAndType(ExperienceTypes experienceType, int level) {
        if(experienceType == ExperienceTypes.FLUCTUATING) {
            return getTotalExperiencePerLevel()[level > 100 ? 99 : level - 1];
        } else if(experienceType == ExperienceTypes.SLOW) {
            return getTotalExperiencePerLevel()[level > 100 ? 199 : level + 99];
        } else if(experienceType == ExperienceTypes.MEDIUM_SLOW) {
            return getTotalExperiencePerLevel()[level > 100 ? 299 : level + 199];
        } else if(experienceType == ExperienceTypes.MEDIUM_FAST) {
            return getTotalExperiencePerLevel()[level > 100 ? 399 : level + 299];
        } else if(experienceType == ExperienceTypes.FAST) {
            return getTotalExperiencePerLevel()[level > 100 ? 499 : level + 399];
        } else if(experienceType == ExperienceTypes.ERRATIC) {
            return getTotalExperiencePerLevel()[level > 100 ? 599 : level + 499];
        } else {
            return -1;
        }
    }
    
    public static int calculateExperienceWon(Pokemon winningPokemon, Pokemon losingPokemon) {
        final int winningPokemonLevel         = winningPokemon.getInformation().getCurrentLevel();
        final int losingPokemonLevel          = losingPokemon.getInformation().getCurrentLevel();
        final int losingPokemonBaseExperience = losingPokemon.getInformation().getBaseExperience();
        
        final int a = (losingPokemonLevel << 1) + 10;
        final int b = losingPokemonBaseExperience * losingPokemonLevel / 5;
        final int c = winningPokemonLevel + losingPokemonLevel + 10;
        
        return (int)(Math.sqrt(a) * (a * a)) * b / (int)(Math.sqrt(c) * (c * c)) + 1;
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
        int[] experienceValues = new int[100];
    
        final int startIndex = (getExperienceType() == ExperienceTypes.FLUCTUATING) ? 0 :
                               (getExperienceType() == ExperienceTypes.SLOW) ? 100 :
                               (getExperienceType() == ExperienceTypes.MEDIUM_SLOW) ? 200 :
                               (getExperienceType() == ExperienceTypes.MEDIUM_FAST) ? 300 :
                               (getExperienceType() == ExperienceTypes.FAST) ? 400 :
                               (getExperienceType() == ExperienceTypes.ERRATIC) ? 500 : -1;
        
        if(startIndex == -1) {
            return 1;
        }
    
        System.arraycopy(getTotalExperiencePerLevel(), startIndex, experienceValues, 0, 100);
        for(int index = 0; index < experienceValues.length; index++) {
            if(getCurrentExperience() < experienceValues[index]) {
                return index;
            }
        }
        return 100;
    }
    
    @Override
    public String toString() {
        return String.format("{Experience Type = %s, Experience Value = %s, Level = %s}", experienceType,
                             currentExperience, getLevel()
        );
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