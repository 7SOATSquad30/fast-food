curl -X POST \
    'https://api.mercadopago.com/users/356453274/stores'\
    -H 'Content-Type: application/json' \
    -H "Authorization: Bearer $MERCADOPAGO_PRIVATE_ACCESS_TOKEN" \
    -d '{
  "business_hours": {
    "monday": [
      {
        "open": "08:00",
        "close": "23:00"
      }
    ],
    "tuesday": [
      {
        "open": "08:00",
        "close": "23:00"
      }
    ],
    "wednesday": [
      {
        "open": "08:00",
        "close": "23:00"
      }
    ],
    "thursday": [
      {
        "open": "08:00",
        "close": "23:00"
      }
    ],
    "friday": [
      {
        "open": "08:00",
        "close": "23:00"
      }
    ],
    "saturday": [
      {
        "open": "13:00",
        "close": "22:00"
      }
    ],
    "sunday": [
      {
        "open": "13:00",
        "close": "22:00"
      }
    ]
  },
  "external_id": "STORE00001",
  "location": {
    "street_number": "1106",
    "street_name": "Av. Paulista",
    "city_name": "São Paulo",
    "state_name": "São Paulo",
    "latitude": -23.5640485,
    "longitude": -46.6526571,
    "reference": ""
  },
  "name": "Fastfood FIAP"
}'