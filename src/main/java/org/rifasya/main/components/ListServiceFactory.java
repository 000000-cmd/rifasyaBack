package org.rifasya.main.components;

import jakarta.annotation.PostConstruct;
import org.rifasya.main.entities.listEntities.ListItemBase;
import org.rifasya.main.services.AbstractListService;
import org.rifasya.main.services.listService.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ListServiceFactory {

    private final ApplicationContext context;
    private final Map<String, AbstractListService<? extends ListItemBase, ?, ?>> serviceMap = new HashMap<>();

    public ListServiceFactory(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void init() {
        // Mapea el 'technicalName' de la URL al servicio correspondiente.
        serviceMap.put("documenttypes", context.getBean(ListDocumentTypeService.class));
        serviceMap.put("gendertypes", context.getBean(ListGenderTypeService.class));
        serviceMap.put("roletypes", context.getBean(ListRoleService.class));
        serviceMap.put("categories", context.getBean(ListCategoryService.class));
        serviceMap.put("externallotteries", context.getBean(ListExternalLotteryService.class));
        serviceMap.put("drawmethods", context.getBean(ListDrawMethodService.class));
        serviceMap.put("rafflemodels", context.getBean(ListRaffleModelService.class));
        serviceMap.put("prizetypes", context.getBean(ListPrizeTypeService.class));
        serviceMap.put("rafflerules", context.getBean(ListRaffleRuleService.class));
    }

    public Optional<AbstractListService<? extends ListItemBase, ?, ?>> getService(String listTechnicalName) {
        return Optional.ofNullable(serviceMap.get(listTechnicalName.toLowerCase()));
    }
}