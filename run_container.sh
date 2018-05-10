docker run -p 8080:8080 \
    -e "sender_email=paulofernandes.tarambola@gmail.com" \
    -e "sender_password=snoof00000" \
    -e "spring_datasource_password=58f4976ddde066f" \
    -e "spring_datasource_username=be550795dbbf32" \
    -e "realemail_api_key=PYJCH6A512N0GLQ3D77TRB45M63K8ZX489S0IWEO2V1F9U" \
    pfernand/pf-mailler
