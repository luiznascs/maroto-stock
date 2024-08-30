package com.javaday.maroto_stock.config.error

import com.javaday.maroto_stock.models.enums.ServiceError
import org.springframework.web.server.ResponseStatusException

class ServiceException(
    serviceError: ServiceError
) : ResponseStatusException(serviceError.status, serviceError.message)