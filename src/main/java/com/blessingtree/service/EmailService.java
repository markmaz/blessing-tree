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
            "*Have you sponsored a child/children through The Blessing Tree Program\n" +
                    "before?:*",
            "*This year, each parent has indicated one wish / per child. How many\n" +
                    "children would you like to sponsor this year?:*",
            "*Do you have an age preference for the child that you would like to\n" +
                    "sponsor?:*",
            "*Do you have a preference on the gender of the child that you would like to\n" +
                    "sponsor?:*",
            "*How did you hear about St. Mary Magdalene’s Blessing Tree Program?:*",
            "*Would you be interested in becoming a volunteer of Magdalene House Social\n" +
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

        List<String> entityValues = new ArrayList<>();

        for(int i = 0; i < EMAIL_VALUES.length - 1; i++){
            String out;

            if (i + 1 <= EMAIL_VALUES.length - 1){
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

        sponsorRepository.save(mapSponsor(entityValues));
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
