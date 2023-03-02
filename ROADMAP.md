 ## Roadmap

* Add boundary checks against the .datelist limits (incl. showcase)
* Adicionar uma fn holidays na api, pra retornar holidays daquela data 
* Improve docs to show how to use the holi api with strings instead of LocalDate instances (except for `add`)
* Non-saturday/sunday weekends

## Boundary checks

add
- Se o resultado passar do limite: estourar, pois não há garantia de que esteja certo
- Isso pra :business-days. O resto de boas.

weekend?
holiday?
non-business-day?
business-day?
- Estourar se a data for fora do limite

### Como saber qual o limite?

Usar luciolucio.holi.core/read-dates

O retorno disso precisa ter ordem, e ser vetor.
Aí é só pegar o primeiro e o último: o limite baixo é 1Jan*Ano do primeiro*. O alto é 31Dec*Ano do último*
