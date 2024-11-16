package com.blessingtree.service;

import com.blessingtree.dto.EmailDTO;
import com.blessingtree.model.Address;
import com.blessingtree.model.Sponsor;
import com.blessingtree.model.SponsorYear;
import com.blessingtree.repository.SponsorRepository;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static final int FIRST_NAME = 0;
    public static final int LAST_NAME = 1;
    public static final int ADDRESS = 2;
    public static final int CITY = 3;
    public static final int STATE = 4;
    public static final int ZIP = 5;
    public static final int EMAIL = 6;
    public static final int PHONE = 7;
    public static final int BEST_TIME_TO_CALL = 8;
    public static final int HAS_SPONSORED_PREVIOUSLY = 9;
    public static final int NUMBER_OF_CHILDREN = 10;
    public static final int CHILD_AGE_PREFERENCE = 11;
    public static final int GENDER_PREFERENCE = 12;
    public static final int HEAR_ABOUT_US = 13;
    public static final int WANT_TO_VOLUNTEER = 14;

    private static final String[] EMAIL_VALUES = {"*First Name:*", "*Last Name:*", "*Address:*", "*City:*", "*State:*",
            "*Zip:*", "*Email:*", "*Phone Number:*", "*Best Time To Call:*",
            "*Have you sponsored a child/children through The Blessing Tree Program before?:*",
            "*This year, each parent has indicated one wish / per child. How many " +
                    "children would you like to sponsor this year?:*",
            "*Do you have an age preference for the child that you would like to " +
                    "sponsor?:*",
            "*Do you have a preference on the gender of the child that you would like to " +
                    "sponsor?:*",
            "*How did you hear about St. Mary Magdalene’s Blessing Tree Program?:*",
            "*Would you be interested in becoming a volunteer of Magdalene House Social " +
                    "Services?:*"};
    private static final String EMAIL_SPLIT = "\\*Blessing Tree Sponsor <https://st-mm.com/blessing-tree-sponsor-survey>\\*";

    public static void main(String[] args){
        EmailDTO emailDTO = getEmail();
        parseBodyAndCreateSponsor(emailDTO);
    }

    public static EmailDTO getEmail(){
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setBody("*First Name:* Terry\n" +
                "*Last Name:* Mares\n" +
                "*Address:* 4862 gypsy forest dr\n" +
                "*City:* Humble\n" +
                "*State:* TX\n" +
                "*Zip:* 77346\n" +
                "*Email:* tmares.3157@gmail.com\n" +
                "*Phone Number:* 8327900837\n" +
                "*Best Time To Call:* Any time\n" +
                "*Have you sponsored a child/children through The Blessing Tree Program\n" +
                "before?:* YES\n" +
                "*This year, each parent has indicated one wish / per child. How many\n" +
                "children would you like to sponsor this year?:* 1\n" +
                "*Do you have an age preference for the child that you would like to\n" +
                "sponsor?:* 6 -12 years\n" +
                "*Do you have a preference on the gender of the child that you would like to\n" +
                "sponsor?:* Either\n" +
                "*How did you hear about St. Mary Magdalene’s Blessing Tree Program?:*\n" +
                "*Would you be interested in becoming a volunteer of Magdalene House Social\n" +
                "Services?:* Not at this time");
        return emailDTO;
    }
    public static int parseBodyAndCreateSponsor(EmailDTO emailDTO){
        String heads = emailDTO.getBody().trim();
        //String heads = body[1].trim();

        heads = heads.replaceAll("\n", " ");
        heads = heads.replaceAll("\r", "");
        List<String> entityValues = new ArrayList<>();

        for(int i = 0; i < EMAIL_VALUES.length ; i++){
            String out;

            if (i < EMAIL_VALUES.length - 1){
                out = heads.substring(heads.indexOf(EMAIL_VALUES[i]) + EMAIL_VALUES[i].length(), heads.indexOf(EMAIL_VALUES[i + 1])).trim();
            }else{
                out = heads.substring(heads.indexOf(EMAIL_VALUES[i]) + EMAIL_VALUES[i].length(), heads.length()).trim();
            }

            if (out.isBlank() || out.isEmpty()){
                entityValues.add("");
            }else{
                entityValues.add(out.replace("\n", " "));
            }
        }

        Sponsor sponsor = mapSponsor(entityValues);
        System.out.println(sponsor);
        return HttpStatus.SC_OK;
    }

    private static Sponsor mapSponsor(List<String> entityValues) {
        Sponsor sponsor = new Sponsor();
        sponsor.setFirstName(entityValues.get(FIRST_NAME));
        sponsor.setLastName(entityValues.get(LAST_NAME));

        Address address = new Address();
        address.setAddress(entityValues.get(ADDRESS));
        address.setCity(entityValues.get(CITY));
        address.setState(entityValues.get(STATE));
        address.setZip(entityValues.get(ZIP));
        sponsor.setAddress(address);

        sponsor.setEmail(entityValues.get(EMAIL));
        sponsor.setPhone(entityValues.get(PHONE));
        sponsor.setBestTimeToCall(entityValues.get(BEST_TIME_TO_CALL));
        sponsor.setHasSponsoredPreviously(convertToBool(entityValues.get(HAS_SPONSORED_PREVIOUSLY)));

        SponsorYear sponsorYear = new SponsorYear();
        sponsorYear.setNumberOfChildrenSponsored(Long.valueOf(entityValues.get(NUMBER_OF_CHILDREN)));
        sponsorYear.setChildAgePreference(entityValues.get(CHILD_AGE_PREFERENCE));
        sponsorYear.setGenderPreference(entityValues.get(GENDER_PREFERENCE));
        sponsor.getSponsorYear().add(sponsorYear);

        sponsor.setHowDidYouHearAboutUs(entityValues.get(HEAR_ABOUT_US));
        sponsor.setWantToVolunteer(convertToBool(entityValues.get(WANT_TO_VOLUNTEER)));

        return sponsor;
    }

    private static Boolean convertToBool(String s) {
        if(s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y")
                || s.equalsIgnoreCase("true")){
            return true;
        }else{
            return false;
        }
    }
}
