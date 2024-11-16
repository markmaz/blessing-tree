package com.blessingtree.service;

import com.blessingtree.dto.EmailDTO;
import com.blessingtree.model.Address;
import com.blessingtree.model.Sponsor;
import com.blessingtree.model.SponsorYear;
import com.blessingtree.repository.SponsorRepository;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService extends BaseService{
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
    private final SponsorRepository sponsorRepository;
    private static final String[] EMAIL_VALUES = {"*First Name:*", "*Last Name:*", "*Address:*", "*City:*", "*State:*",
            "*Zip:*", "*Email:*", "*Phone Number:*", "*Best Time To Call:*",
            "*Have you sponsored a child/children through The Blessing Tree Program " +
                    "before?:*",
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

    public EmailService(@Autowired ModelMapper mapper,
                        @Autowired SponsorRepository sponsorRepository){
        super(mapper);
        this.sponsorRepository = sponsorRepository;
    }

    public int parseBodyAndCreateSponsor(EmailDTO emailDTO){
        String[] body = emailDTO.getBody().split(EMAIL_SPLIT);
        String heads = body[1].trim();
        heads = heads.replaceAll("\n", " ");
        heads = heads.replaceAll("\r", "");
        heads = heads.replaceAll("\u00A0", "");
        List<String> entityValues = new ArrayList<>();

        for(int i = 0; i < EMAIL_VALUES.length; i++){
            String out ="";

            int startIndex = heads.indexOf(EMAIL_VALUES[i]);

            if (startIndex != -1) { // Only proceed if the start delimiter is found
                int endIndex = (i < EMAIL_VALUES.length - 1) ? heads.indexOf(EMAIL_VALUES[i + 1]) : heads.length();

                if (endIndex != -1 && endIndex > startIndex) { // Ensure the end index is valid
                    out = heads.substring(startIndex + EMAIL_VALUES[i].length(), endIndex).trim();
                } else if (endIndex == -1) {
                    // If there's no end delimiter, extract the substring till the end of the string
                    out = heads.substring(startIndex + EMAIL_VALUES[i].length()).trim();
                }
            }

            System.out.println(out);
        }

        //sponsorRepository.save(mapSponsor(entityValues));
        return HttpStatus.SC_OK;
    }

    private Sponsor mapSponsor(List<String> entityValues) {
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

    private Boolean convertToBool(String s) {
        if(s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y")
                || s.equalsIgnoreCase("true")){
            return true;
        }else{
            return false;
        }
    }
}
