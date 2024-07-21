curl -X POST \
    'https://api.mercadopago.com/pos'\
    -H 'Content-Type: application/json' \
    -H "Authorization: Bearer $MERCADOPAGO_PRIVATE_ACCESS_TOKEN" \
    -d '{
  "category": 5611203,
  "external_id": "STORE00001POS001",
  "external_store_id": "STORE00001",
  "fixed_amount": true,
  "name": "First POS",
  "store_id": 62212711
}'