package com.senati.itanesturismo.data.remote.dto;

public record JSendResponse<T>(String status, T data) { }
