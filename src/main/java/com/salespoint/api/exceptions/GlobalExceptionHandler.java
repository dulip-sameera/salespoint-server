package com.salespoint.api.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.salespoint.api.exceptions.customs.customer.CustomerAlreadyExistsException;
import com.salespoint.api.exceptions.customs.customer.CustomerNameInvalidException;
import com.salespoint.api.exceptions.customs.customer.CustomerNotFoundException;
import com.salespoint.api.exceptions.customs.customer.CustomerPhoneNumberInvalidException;
import com.salespoint.api.exceptions.customs.customer.CustomerStatusNotFoundException;
import com.salespoint.api.exceptions.customs.item.ItemAlreadyExistsException;
import com.salespoint.api.exceptions.customs.item.ItemCategoryAlreadyExistsException;
import com.salespoint.api.exceptions.customs.item.ItemCategoryNotFoundException;
import com.salespoint.api.exceptions.customs.item.ItemInvalidQuantityException;
import com.salespoint.api.exceptions.customs.item.ItemNotFoundException;
import com.salespoint.api.exceptions.customs.item.ItemPriceInvalidException;
import com.salespoint.api.exceptions.customs.item.ItemQuantityAvailableException;
import com.salespoint.api.exceptions.customs.item.ItemStatusNotFoundException;
import com.salespoint.api.exceptions.customs.order.ItemCantBeAddedAfterOrderPaidException;
import com.salespoint.api.exceptions.customs.order.ItemCantBeRemovedAfterOrderPaidException;
import com.salespoint.api.exceptions.customs.order.OrderCantBeDeletedAfterPaidException;
import com.salespoint.api.exceptions.customs.order.OrderNotFoundException;
import com.salespoint.api.exceptions.customs.order.OrderedItemDoesNotExistsException;
import com.salespoint.api.exceptions.customs.stock.ItemOutOfStockException;
import com.salespoint.api.exceptions.customs.stock.StockInvalidQuantityException;
import com.salespoint.api.exceptions.customs.stock.StockNotFoundException;
import com.salespoint.api.exceptions.customs.user.UsernameAlreadyExistsException;
import com.salespoint.api.exceptions.customs.user.InvalidFullNameLengthException;
import com.salespoint.api.exceptions.customs.user.InvalidPasswordLengthException;
import com.salespoint.api.exceptions.customs.user.InvalidUsernameLengthException;
import com.salespoint.api.exceptions.customs.user.UserNotFoundException;
import com.salespoint.api.exceptions.customs.user.UserRoleNotFoundException;
import com.salespoint.api.exceptions.customs.user.UserStatusNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ----------------- Security Exceptions-------------

    @ExceptionHandler({ Exception.class })
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");

            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");

            return errorDetail;
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");

            return errorDetail;
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");

            return errorDetail;
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");

            return errorDetail;
        }

        if (exception instanceof DisabledException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Account has been disabled");

            return errorDetail;
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown Internal Server Error");
        }
        return errorDetail;
    }

    // -----------Customer Exceptions-----------

    @ExceptionHandler({ CustomerNotFoundException.class })
    public ProblemDetail handleCustomerNotFoundException(CustomerNotFoundException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "Customer does not exists");

        return errorDetail;
    }

    @ExceptionHandler({ CustomerAlreadyExistsException.class })
    public ProblemDetail handleCustomerAlreadyExistsException(CustomerAlreadyExistsException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", "Customer already exists");

        return errorDetail;
    }

    @ExceptionHandler({ CustomerStatusNotFoundException.class })
    public ProblemDetail handleCustomerStatusNotFoundException(CustomerNotFoundException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "Customer Status does not exists");

        return errorDetail;
    }

    @ExceptionHandler({ CustomerPhoneNumberInvalidException.class })
    public ProblemDetail handleCustomerPhoneNumberInvalidException(CustomerPhoneNumberInvalidException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", "Invalid Phone Number");

        return errorDetail;

    }

    @ExceptionHandler({ CustomerNameInvalidException.class })
    public ProblemDetail handleCustomerNameInvalidException(CustomerNameInvalidException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", exception.getMessage());

        return errorDetail;
    }

    // -----------Item Exceptions-----------

    @ExceptionHandler({ ItemNotFoundException.class })
    public ProblemDetail handleItemNotFoundException(ItemNotFoundException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "Item does not exists");

        return errorDetail;
    }

    @ExceptionHandler({ ItemStatusNotFoundException.class })
    public ProblemDetail handleItemStatusNotFoundException(ItemStatusNotFoundException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "Item Status does not exists");

        return errorDetail;
    }

    @ExceptionHandler({ ItemAlreadyExistsException.class })
    public ProblemDetail handleItemAlreadyExistsException(ItemAlreadyExistsException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "Item already exists");

        return errorDetail;
    }

    @ExceptionHandler({ ItemCategoryNotFoundException.class })
    public ProblemDetail handleItemCategoryNotFoundException(ItemCategoryNotFoundException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "Item Category does not exists");

        return errorDetail;
    }

    @ExceptionHandler({ ItemCategoryAlreadyExistsException.class })
    public ProblemDetail handleItemCategoryAlreadyExistsException(ItemCategoryAlreadyExistsException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", "Item Category already exists");

        return errorDetail;
    }

    @ExceptionHandler({ ItemPriceInvalidException.class })
    public ProblemDetail handleItemPriceInvalidException(ItemPriceInvalidException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", "Price should be greater than or equal to zero");

        return errorDetail;
    }

    @ExceptionHandler({ ItemInvalidQuantityException.class })
    public ProblemDetail handleItemInvalidQuantityException(ItemInvalidQuantityException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", "Quantity should be greater than or equal to zero");

        return errorDetail;
    }

    @ExceptionHandler({ ItemQuantityAvailableException.class })
    public ProblemDetail handleItemQuantityAvailableException(ItemQuantityAvailableException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", "Item Quantity still available");

        return errorDetail;
    }

    // -----------Stock Exceptions--------------------

    @ExceptionHandler({ StockNotFoundException.class })
    public ProblemDetail handleStockNotFoundException(StockNotFoundException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "Stock not found");

        return errorDetail;
    }

    @ExceptionHandler({ StockInvalidQuantityException.class })
    public ProblemDetail handleStockInvalidQuantityException(StockInvalidQuantityException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", "Invalid Stock Quantity");

        return errorDetail;
    }

    @ExceptionHandler({ ItemOutOfStockException.class })
    public ProblemDetail handleItemOutOfStockException(ItemOutOfStockException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", "Item out of stock");

        return errorDetail;
    }

    // -----------User Exceptions--------------------

    @ExceptionHandler({ UserStatusNotFoundException.class })
    public ProblemDetail handleUserStatusNotFoundException(UserStatusNotFoundException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "User status not found");

        return errorDetail;
    }

    @ExceptionHandler({ UserNotFoundException.class })
    public ProblemDetail handleUserNotFoundException(UserNotFoundException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "User not found");

        return errorDetail;
    }

    @ExceptionHandler({ UsernameAlreadyExistsException.class })
    public ProblemDetail handleUserAlreadyExistsException(UsernameAlreadyExistsException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", "Username already exists");

        return errorDetail;
    }

    @ExceptionHandler({ UserRoleNotFoundException.class })
    public ProblemDetail handleUserRoleNotFoundException(UserRoleNotFoundException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "User role not found");

        return errorDetail;
    }

    @ExceptionHandler({ InvalidFullNameLengthException.class })
    public ProblemDetail handleInvalidFullNameLengthException(InvalidFullNameLengthException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", exception.getMessage());

        return errorDetail;
    }

    @ExceptionHandler({ InvalidPasswordLengthException.class })
    public ProblemDetail handleInvalidPasswordLengthException(InvalidPasswordLengthException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", exception.getMessage());

        return errorDetail;
    }

    @ExceptionHandler({ InvalidUsernameLengthException.class })
    public ProblemDetail handleInvalidUsernameLengthException(InvalidUsernameLengthException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                exception.getMessage());
        errorDetail.setProperty("description", exception.getMessage());

        return errorDetail;
    }

    // -----------Order Exceptions--------------------

    @ExceptionHandler({ OrderNotFoundException.class })
    public ProblemDetail handleOrderNotFoundException(OrderNotFoundException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "Order not found");

        return errorDetail;
    }

    @ExceptionHandler({ ItemCantBeAddedAfterOrderPaidException.class })
    public ProblemDetail handleItemCantBeAddedAfterOrderPaidException(
            ItemCantBeAddedAfterOrderPaidException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
                exception.getMessage());
        errorDetail.setProperty("description", "Items can't be added after order has been paid");

        return errorDetail;
    }

    @ExceptionHandler({ ItemCantBeRemovedAfterOrderPaidException.class })
    public ProblemDetail handleItemCantBeRemovedAfterOrderPaidException(
            ItemCantBeRemovedAfterOrderPaidException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
                exception.getMessage());
        errorDetail.setProperty("description", "Items can't be removed after order has been paid");

        return errorDetail;
    }

    @ExceptionHandler({ OrderedItemDoesNotExistsException.class })
    public ProblemDetail handleOrderedItemDoesNotExistsException(OrderedItemDoesNotExistsException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "Item not found in the order");

        return errorDetail;
    }

    @ExceptionHandler({ OrderCantBeDeletedAfterPaidException.class })
    public ProblemDetail handleOrderCantBeDeletedAfterPaidException(OrderCantBeDeletedAfterPaidException exception) {

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
                exception.getMessage());
        errorDetail.setProperty("description", "Order can't be deleted after order has been paid");

        return errorDetail;
    }

    // ----------------- General Exception-------------

    @ExceptionHandler({ RuntimeException.class })
    public ProblemDetail handleRuntimeException(RuntimeException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),
                exception.getMessage());
        errorDetail.setProperty("description", "Unknown Internal Server Error");

        return errorDetail;
    }
}
