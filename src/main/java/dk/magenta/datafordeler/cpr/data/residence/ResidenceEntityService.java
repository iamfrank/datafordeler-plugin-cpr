package dk.magenta.datafordeler.cpr.data.residence;

import dk.magenta.datafordeler.core.exception.AccessDeniedException;
import dk.magenta.datafordeler.core.exception.AccessRequiredException;
import dk.magenta.datafordeler.core.fapi.FapiService;
import dk.magenta.datafordeler.core.user.DafoUserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lars on 19-05-17.
 */
@Controller
@RequestMapping("/cpr/residence/1/rest")
public class ResidenceEntityService extends FapiService<ResidenceEntity, ResidenceQuery> {

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public String getServiceName() {
        return "residence";
    }

    @Override
    protected Class<ResidenceEntity> getEntityClass() {
        return ResidenceEntity.class;
    }

    @Override
    protected void checkAccess(DafoUserDetails dafoUserDetails) throws AccessDeniedException, AccessRequiredException {
    }

    @Override
    protected ResidenceQuery getEmptyQuery() {
        return new ResidenceQuery();
    }

}
